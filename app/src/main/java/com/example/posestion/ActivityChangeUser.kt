package com.example.posestion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityChangeUserBinding
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Timer
import kotlin.concurrent.timer

class ActivityChangeUser : AppCompatActivity() {

    private val binding: ActivityChangeUserBinding by lazy { ActivityChangeUserBinding.inflate(layoutInflater) }
    private lateinit var spinner_phone : Spinner
    private lateinit var pwcheckedit : EditText
    private lateinit var pwedit : EditText
    private lateinit var nickedit : EditText
    private lateinit var nickchecktext: TextView
    private lateinit var pwchecktext : TextView
    private lateinit var pwtext : TextView
    private lateinit var timerTask : Timer
    private lateinit var pw : String
    private lateinit var phonenum : String
    private lateinit var imagePart: MultipartBody.Part
    private lateinit var changeprofileimage: ImageButton
    private lateinit var profilenickname: String
    private var profile: CircleImageView? = null
    private var pwcheck = false
    private var nickcheck = false
    private var timer = 0

    //비밀번호 일치하는지 확인
    private val pwcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwchecktext.visibility = View.INVISIBLE
            } else {
                pwchecktext.visibility = View.VISIBLE
                if (inputText == pwedit.text.toString()) {
                    pwchecktext.text = "비밀번호가 일치합니다."
                    pwcheck = true
                } else {
                    pwchecktext.text = "비밀번호가 일치하지 않습니다."
                    pwcheck = false
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //비밀번호 양식 확인
    private val pwwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwtext.visibility = View.INVISIBLE
            } else {
                pwtext.visibility = View.VISIBLE
                val pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,30}$".toRegex()

                if (inputText.matches(pattern)) {
                    pwtext.visibility = View.INVISIBLE
                } else {
                    pwtext.text = "비밀번호 형식이 올바르지 않습니다."
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //닉네임 체크 문구
    private val nickcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (nickchecktext.visibility == View.VISIBLE) {
                nickchecktext.visibility = View.INVISIBLE
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //한줄 소개 글자 수
    private val introwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val length = binding.AchangeEditIntro.text.length
            binding.AchangeTextIntro.text = "${length}/20"
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

        pwcheckedit = binding.AchangeEditPwcheck
        pwedit = binding.AchangeEditPw
        pwchecktext = binding.AchangeTextPwcheck
        pwtext = binding.AchangeTextPw
        nickedit = binding.AchangeEditNick
        nickchecktext = binding.AchangeTextChecknick
        profile = binding.AchangeImageProfile

        pwedit.addTextChangedListener(pwwatcherListener)
        pwcheckedit.addTextChangedListener(pwcheckwatcherListener)
        nickedit.addTextChangedListener(nickcheckwatcherListener)
        binding.AchangeEditIntro.addTextChangedListener(introwatcherListener)

        //toolbar 설정
        setSupportActionBar(binding.AchangeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //spinner 설정
        val dpValue = 66
        val pixels = (dpValue * Resources.getSystem().displayMetrics.density).toInt()

        spinner_phone = binding.AchangeSpinner
        spinner_phone.setDropDownWidth(pixels)

        ArrayAdapter.createFromResource(this, R.array.phone_spinner_item, R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_phone.adapter = adapter
        }

        //회원 탈퇴 눌렀을 때
        val out = binding.AchangeTextOut
        val spanout = SpannableStringBuilder("회원 탈퇴")
        val clickout = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, ActivityWithdraw::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#ABABAB")
            }
        }

        spanout.setSpan(clickout, 0, spanout.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        out.text = spanout
        out.movementMethod = LinkMovementMethod.getInstance()

        //인증번호 받기 눌렀을 때 동작
        binding.AchangeBtnNum.setOnClickListener{
            setTimer()
            binding.AchangeBtnNum.isEnabled = false
        }

        //재전송 눌렀을 때
        val resend = binding.AchangeTextResend
        val spanresend = SpannableStringBuilder("재전송")
        val clickresend = object : ClickableSpan() {
            override fun onClick(view: View) {
                timerTask.cancel()
                setTimer()
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#5F5F5F")
            }
        }

        //인증번호 확인 버튼 눌렀을 때 동작
        binding.AchangeBtnChecknum.setOnClickListener {
            timerTask.cancel()
            binding.AchangeTextNum.text = "03:00"
            //binding.AsignupBtnChecknum.isClickable = false
        }

        spanresend.setSpan(clickresend, 0, spanresend.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        resend.text = spanresend
        resend.movementMethod = LinkMovementMethod.getInstance()

        //회원정보 수정 눌렀을 때 동작
        binding.AchangeBtnChange.setOnClickListener {

        }

        //닉네임 중복확인
        binding.AchangeBtnChecknick.setOnClickListener {
            profilenickname = nickedit.text.toString()
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

        initImageViewProfile()
    }

    private fun setTimer() {
        timer = 180
        timerTask = timer(period = 1000, initialDelay = 1000) {
            runOnUiThread {
                timer_text()
            }
            timer -= 1
            if (timer == 0) {
                cancel()
                runOnUiThread {
                    binding.AchangeTextNum.text = "03:00"
                }
            }
        }
    }

    private fun timer_text() {

        var tm = timer / 60
        var ts = timer % 60
        var tmtext = String.format("%02d", tm)
        var tstext = String.format("%02d", ts)

        binding.AchangeTextNum.text = "${tmtext}:${tstext}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initImageViewProfile() {
        changeprofileimage = binding.AchangeBtnProfile

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