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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.posestion.databinding.ActivityLoginBinding

class Activity_Login : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AloginToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val Singup = binding.AloginSignup
        val spanup = SpannableStringBuilder("회원가입")
        val clickSingup = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, Activity_terms::class.java)
                view.context.startActivity(intent)
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#9C9C9C")
                tt.isUnderlineText = false
            }
        }

        val find = binding.AloginFind
        val spanfind = SpannableStringBuilder("ID/PW 찾기")
        val clickfind = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, Activity_terms::class.java)
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

        spanfind.setSpan(clickfind, 0, spanfind.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        find.text = spanfind
        find.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, Activity_Start::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}