package com.example.posestion

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.posestion.databinding.ActivityBoardCreationPageBinding
import android.Manifest
import android.text.Editable
import android.text.TextWatcher

class board_creation_page : AppCompatActivity() {
    private lateinit var binding: ActivityBoardCreationPageBinding
    private var imageUriString: String? = null

    companion object {
        private const val REQUEST_CODE_IMAGE_PICK = 1
        private const val REQUEST_CODE_PERMISSION = 2
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.boardCreationPhotobox.setImageURI(it)
            imageUriString = it.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardCreationPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.boardCreationPhotobox.setOnClickListener(){
            checkPermissionsAndOpenGallery()
        }

        binding.boardCreationUploadBtn.setOnClickListener {
            var titletext = binding.boardCreationTitle.text.toString()
            var contentstext = binding.boardCreationContent.text.toString()
            val heart:Boolean = false
            val heartcount:Int = 0
            val commentcount:Int = 0
            var intent = Intent(this,board_contents_view::class.java)
            intent.putExtra("titletext","${titletext}")
            intent.putExtra("contentstext","${contentstext}")
            intent.putExtra("heart",heart)
            intent.putExtra("heartcount",heartcount)
            intent.putExtra("commentcount",commentcount)
            intent.putExtra("imageUriString", imageUriString)
            startActivity(intent)
        }

        binding.boardCreationContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val charCount = s?.length ?: 0
                binding.boardCreationContentcount.text = "${charCount}"
            }

        })
    }

    private fun checkPermissionsAndOpenGallery(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION
            )
        } else {
            openGallery()
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK&&data!=null){
            if(requestCode == REQUEST_CODE_IMAGE_PICK){
                var imageUri : Uri? = data?.data
                binding.boardCreationPhotobox.setImageURI(imageUri)
                imageUriString = imageUri.toString()
            }
        }
    }
}