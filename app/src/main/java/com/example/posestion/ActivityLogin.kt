package com.example.posestion

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogin : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    //자동 로그인
    private val user = MyApplication.user
    private val editor = user.edit()

    //뒤로가기 버튼 동작 제어
    private var backKeyPressedTime = 0
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis().toInt() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis().toInt()
                Toast.makeText(this@ActivityLogin, "\'뒤로가기\' 버튼을 한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                finishAffinity()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //자동로그인 check여부 파악
        binding.AloginAuto.setOnCheckedChangeListener { check, isChecked ->
            if (isChecked) {
                editor.putBoolean("autologin", true)
            } else {
                editor.putBoolean("autologin", false)
            }
            editor.apply()
        }

        //뒤로가기 버튼 이벤트
        this.onBackPressedDispatcher.addCallback(this, callback)

        //toolbar 설정
        setSupportActionBar(binding.AloginToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.image_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //간편 가입하기 text클릭 이벤트 처리
        val Singup = binding.AloginTextSignup
        val spanup = SpannableStringBuilder("간편 가입하기")
        val clickSingup = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, Activityterms::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#9C9C9C")
                tt.isUnderlineText = false
            }
        }

        spanup.setSpan(clickSingup, 0, spanup.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        Singup.text = spanup
        Singup.movementMethod = LinkMovementMethod.getInstance()

        //ID 찾기 text클릭 이벤트 처리
        val findid = binding.AloginFindId
        val spanfindid = SpannableStringBuilder("ID 찾기")
        val clickfindid = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, ActivityFindid::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#ABABAB")
            }
        }

        spanfindid.setSpan(clickfindid, 0, spanfindid.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        findid.text = spanfindid
        findid.movementMethod = LinkMovementMethod.getInstance()

        //PW 찾기 text클릭 이벤트 처리
        val findpw = binding.AloginFindPw
        val spanfindpw = SpannableStringBuilder("PW 찾기")
        val clickfindpw = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, ActivityFindpw::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#ABABAB")
            }
        }

        spanfindpw.setSpan(clickfindpw, 0, spanfindpw.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        findpw.text = spanfindpw
        findpw.movementMethod = LinkMovementMethod.getInstance()

        //로그인 버튼 클릭 이벤트 처리
        binding.AloginBtnLogin.setOnClickListener {
            val id = binding.AloginId.text.toString()
            val pw = binding.AloginPw.text.toString()

            val call = RetrofitObject.getRetrofitService.login(RetrofitClient.Requestlogin(id, pw))
            call.enqueue(object : Callback<RetrofitClient.Responselogin> {
                override fun onResponse(call: Call<RetrofitClient.Responselogin>, response: Response<RetrofitClient.Responselogin>) {
                    if (response.isSuccessful) {
                        val response = response.body()
                        if(response != null){
                            if(response.isSuccess){
                                val auto = user.getBoolean("autologin", false)
                                val token = response.result.jwt
                                if(auto){
                                    editor.putString("id", id)
                                    editor.putString("pw", pw)
                                    editor.putString("jwt", token)
                                    editor.apply()
                                }
                                val intent = Intent(this@ActivityLogin, loginsuccess::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient.Responselogin>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }
    }

    //toolbar menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.empty_menu, menu)
        return true
    }

    //toolbar menu처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ActivityFirst::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}