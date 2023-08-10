package com.example.posestion

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.posestion.databinding.ActivityBoardClassCreationPageBinding
import android.Manifest
import android.app.Activity

class board_class_creation_page : AppCompatActivity() {
    private lateinit var binding: ActivityBoardClassCreationPageBinding
    private var imageUriString: String? = null

    companion object {
        private const val REQUEST_CODE_IMAGE_PICK = 1
        private const val REQUEST_CODE_PERMISSION = 2
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.boardClassCreationPhotobox.setImageURI(it)
            imageUriString = it.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardClassCreationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardClassCreationPhotobox.setOnClickListener {
            checkPermissionsAndOpenGallery()
        }

        binding.boardClassCreationUploadBtn.setOnClickListener {
            var titletext = binding.boardClassCreationTitle.text.toString()
            var contentstext = binding.boardClassCreationContent.text.toString()
        }
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
                binding.boardClassCreationPhotobox.setImageURI(imageUri)
                imageUriString = imageUri.toString()
            }
        }
    }
}