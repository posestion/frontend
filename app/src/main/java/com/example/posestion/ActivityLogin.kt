package com.example.posestion

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.example.posestion.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
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

        this.onBackPressedDispatcher.addCallback(this, callback)

        setSupportActionBar(binding.AloginToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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

        val findid = binding.AloginFindId
        val spanfindid = SpannableStringBuilder("ID 찾기")
        val clickfindid = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, ActivityFindid::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#9C9C9C")
            }
        }

        val findpw = binding.AloginFindPw
        val spanfindpw = SpannableStringBuilder("PW 찾기")
        val clickfindpw = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, ActivityFindpw::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#9C9C9C")
            }
        }

        spanup.setSpan(clickSingup, 0, spanup.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        Singup.text = spanup
        Singup.movementMethod = LinkMovementMethod.getInstance()

        spanfindid.setSpan(clickfindid, 0, spanfindid.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        findid.text = spanfindid
        findid.movementMethod = LinkMovementMethod.getInstance()

        spanfindpw.setSpan(clickfindpw, 0, spanfindpw.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        findpw.text = spanfindpw
        findpw.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ActivityFirst::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}