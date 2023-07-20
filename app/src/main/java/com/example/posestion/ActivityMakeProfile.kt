package com.example.posestion

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.posestion.databinding.ActivityMakeProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMakeProfile : AppCompatActivity() {

    private val binding by lazy { ActivityMakeProfileBinding.inflate(layoutInflater) }
    private var profile: ImageView? = null
    private var nickcheck = false
    private lateinit var changeprofileimage: ImageButton
    private lateinit var nickchecktext : TextView

    private val nickcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty() && nickchecktext.visibility == View.VISIBLE) {
                nickchecktext.visibility = View.INVISIBLE
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data
        if (resultCode == Activity.RESULT_OK) {
            val profileimage: Uri? = data?.data
            if (profileimage != null) {
                profile = binding.AmakeprofileImage
                profile!!.setImageURI(profileimage)

            } else {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initImageViewProfile()

        setSupportActionBar(binding.AmakeprofileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.AmakeprofileEditNick.addTextChangedListener(nickcheckwatcherListener)

        binding.AmakeprofileBtnNickname.setOnClickListener {
            val nickname = binding.AmakeprofileEditNick.text.toString()
            val call = RetrofitObject.getRetrofitService.checknickname(nickname)
            call.enqueue(object : Callback<Responsenickname> {
                override fun onResponse(call: Call<Responsenickname>, response: Response<Responsenickname>) {
                    if (response.isSuccessful) {
                        val response = response.body()
                        if(response != null){
                            val nickchecktext = binding.AmakeprofileTextNick
                            if(response.message.toString() == "성공"){
                                nickcheck = true
                                nickchecktext.text = "사용가능한 닉네임 입니다."
                                nickchecktext.visibility = View.VISIBLE
                            }
                            else{
                                nickcheck = false
                                nickchecktext.text = "이미 사용중인 닉네임 입니다."
                                nickchecktext.visibility = View.VISIBLE
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Responsenickname>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initImageViewProfile() {
        changeprofileimage = binding.AmakeprofileBtnImage

        changeprofileimage.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    imageload()
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    imageload()
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
    }

    private fun imageload() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 설정하기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }
}