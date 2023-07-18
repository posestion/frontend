package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityLoginsuccessBinding

class loginsuccess : AppCompatActivity() {

    private val binding: ActivityLoginsuccessBinding by lazy { ActivityLoginsuccessBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val editor = user.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            editor.putBoolean("autologin", false)
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }
    }
}