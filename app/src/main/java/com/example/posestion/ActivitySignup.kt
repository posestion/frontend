package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.posestion.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.timer

class ActivitySignup : AppCompatActivity() {

    private val binding: ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private lateinit var spinner_phone : Spinner
    private lateinit var pwcheck : EditText
    private lateinit var pw : EditText
    private lateinit var pwchecktext : TextView
    private lateinit var pwtext : TextView
    private lateinit var timerTask : Timer
    private var timer = 0

    private val pwcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwchecktext.visibility = View.INVISIBLE
            } else {
                pwchecktext.visibility = View.VISIBLE
                if (inputText == pw.text.toString()) {
                    pwchecktext.text = "비밀번호가 일치합니다."
                } else {
                    pwchecktext.text = "비밀번호가 일치하지 않습니다."
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val pwwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwtext.visibility = View.INVISIBLE
            } else {
                pwtext.visibility = View.VISIBLE
                val pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,}$".toRegex()

                if (inputText.matches(pattern)) {
                    pwtext.visibility = View.INVISIBLE
                } else {
                    pwtext.text = "비밀번호 형식이 일치하지 않습니다."
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pwcheck = binding.AsignupEditPwCheck
        pw = binding.AsignupEditPw
        pwchecktext = binding.AsignupTextPwcheck
        pwtext = binding.AsignupTextPw

        pw.addTextChangedListener(pwwatcherListener)
        pwcheck.addTextChangedListener(pwcheckwatcherListener)

        setSupportActionBar(binding.AsignupToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val dpValue = 66
        val pixels = (dpValue * Resources.getSystem().displayMetrics.density).toInt()

        spinner_phone = binding.AsignupSpinner
        spinner_phone.setDropDownWidth(pixels)

        ArrayAdapter.createFromResource(this, R.array.phone_spinner_item, R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_phone.adapter = adapter
        }

        binding.AsignupBtnNext.setOnClickListener {
            val intent = Intent(this, ActivityMakeProfile::class.java)
            startActivity(intent)
        }

        binding.AsignupBtnCheckid.setOnClickListener {
            val id = binding.AsignupEditId.text.toString()
            val call = RetrofitObject.getRetrofitService.checkid(id)
            call.enqueue(object : Callback<Responsecheckid> {
                override fun onResponse(call: Call<Responsecheckid>, response: Response<Responsecheckid>) {
                    if (response.isSuccessful) {
                        val response = response.body()
                        if(response != null){
                            val idcheck = binding.AsignupTextIdcheck
                            if(response.message.toString() == "성공"){
                                idcheck.text = "사용가능한 아이디 입니다."
                                idcheck.visibility = View.VISIBLE
                            }
                            else{
                                idcheck.text = "이미 사용중인 아이디 입니다."
                                idcheck.visibility = View.VISIBLE
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Responsecheckid>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }

        //인증번호 받기 눌렀을 때 동작
        binding.AsignupBtnSendnum.setOnClickListener{
            setTimer()
        }

        //재전송 눌렀을 때 동작
        val resend = binding.AsignupTextResend
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

        spanresend.setSpan(clickresend, 0, spanresend.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        resend.text = spanresend
        resend.movementMethod = LinkMovementMethod.getInstance()

        //인증번호 확인 버튼 눌렀을 때 동작
        binding.AsignupBtnChecknum.setOnClickListener {
            timerTask.cancel()
            binding.AsignupTextNum.text = "03:00"
            //binding.AsignupBtnChecknum.isClickable = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, Activityterms::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                    binding.AsignupTextNum.text = "03:00"
                }
            }
        }
    }

    private fun timer_text() {

        var tm = timer / 60
        var ts = timer % 60
        var tmtext = String.format("%02d", tm)
        var tstext = String.format("%02d", ts)

        binding.AsignupTextNum.text = "${tmtext}:${tstext}"
    }
}