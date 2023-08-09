package com.example.posestion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityMakeProfileBinding
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ActivityMakeProfile : AppCompatActivity() {

    private val binding by lazy { ActivityMakeProfileBinding.inflate(layoutInflater) }
    private var profile: CircleImageView? = null
    private var nickcheck = false
    private var y = "0000"
    private var m2 = "00"
    private var d2 = "00"
    private lateinit var changeprofileimage: ImageButton
    private lateinit var nickchecktext: TextView
    private lateinit var profilenickname: String
    private lateinit var imagePart: MultipartBody.Part

    private val nickcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            nickchecktext = binding.AmakeprofileTextNick
            if (nickchecktext.visibility == View.VISIBLE) {
                nickchecktext.visibility = View.INVISIBLE
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val introwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val length = binding.AmakeprofileEditIntro.text.length
            binding.AmakeprofileTextIntro.text = "${length}/20"
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //이미지 불러오기
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data
        if (resultCode == Activity.RESULT_OK) {
            val profileimage: Uri? = data?.data
            if (profileimage != null) {
                profile!!.setImageURI(profileimage)
                val path = getRealPathFromUri(profileimage)
                val file = File(path)
                val mediaType = "image/*".toMediaTypeOrNull()
                val imageRequestBody = file.asRequestBody(mediaType)
                imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequestBody)
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

        profile = binding.AmakeprofileImage

        //프로필 설정하지 않았을 때 기본 프로필
        val resourceId = R.drawable.profilejpg
        val newfile = saveResourceImageToFile(this, resourceId)
        val path = newfile.absolutePath
        val file = File(path)
        val mediaType = "image/*".toMediaTypeOrNull()
        val imageRequestBody = file.asRequestBody(mediaType)
        imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequestBody)

        //프로필 생성 버튼 눌렀을 때 동작
        binding.AmakeprofileBtnEnd.setOnClickListener {
            if(binding.AmakeprofileBirth.text.length == 8){
                val birthday = binding.AmakeprofileBirth.text.toString().toInt()
                y = (birthday/10000).toString()
                val m = (birthday%10000)/100
                val d = (birthday%10000)%100
                m2 = String.format("%02d", m)
                d2 = String.format("%02d", d)
            }

            val marketingAgreement = "true".toRequestBody("text/plain".toMediaTypeOrNull())
            val userId = intent.getStringExtra("id").toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val password = intent.getStringExtra("pw").toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneNumber = intent.getStringExtra("phonenum").toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val birth = "${y}-${m2}-${d2}".toRequestBody("text/plain".toMediaTypeOrNull())
            val nickname = profilenickname.toRequestBody("text/plain".toMediaTypeOrNull())
            val username = intent.getStringExtra("name").toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val call = RetrofitObject.getRetrofitService
            call.signup(marketingAgreement, userId, password, phoneNumber, birth, nickname, username, imagePart)
                .enqueue(object : Callback<RetrofitClient.ResponseSignup> {
                    override fun onResponse(call: Call<RetrofitClient.ResponseSignup>, response: Response<RetrofitClient.ResponseSignup>) {
                        if (response.isSuccessful) {
                            Log.d("Retrofit", "success")
                            val result = response.body()
                            Log.d("Retrofit", result.toString())
                            if(result != null){
                                Log.d("Retrofit", result.message.toString())
                                val intent = Intent(this@ActivityMakeProfile, ActivityLogin::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.d("Retrofit", "Response Error: $errorBody")
                        }
                    }
                    override fun onFailure(call: Call<RetrofitClient.ResponseSignup>, t: Throwable) {
                        val errorMessage = "Call Failed: ${t.message}"
                        Log.d("Retrofit", errorMessage)
                    }
                })
        }

        initImageViewProfile()

        setSupportActionBar(binding.AmakeprofileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.AmakeprofileEditNick.addTextChangedListener(nickcheckwatcherListener)

        //닉네임 중복 확인
        binding.AmakeprofileBtnNickname.setOnClickListener {
            profilenickname = binding.AmakeprofileEditNick.text.toString()
            val call = RetrofitObject.getRetrofitService.checknickname(profilenickname)
            call.enqueue(object : Callback<RetrofitClient.Responsenickname> {
                override fun onResponse(call: Call<RetrofitClient.Responsenickname>, response: Response<RetrofitClient.Responsenickname>) {
                    if (response.isSuccessful) {
                        val response = response.body()
                        if(response != null){
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

                override fun onFailure(call: Call<RetrofitClient.Responsenickname>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.empty_menu, menu)
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

    //프로필 선택 버튼 눌렀을 때 동작
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

    private fun getRealPathFromUri(uri: Uri): String? {
        val context = applicationContext
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }

    fun saveResourceImageToFile(context: Context, resourceId: Int): File {
        val inputStream = context.resources.openRawResource(resourceId)
        val file = File(context.cacheDir, "temp_image.jpg")

        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return file
    }
}