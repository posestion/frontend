package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivitySuccessSignupBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ActivitySuccessSignup : AppCompatActivity() {

    private val binding: ActivitySuccessSignupBinding by lazy { ActivitySuccessSignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        binding.AsuccesssignupText.text = "${name}님 포세션 가입을\n축하합니다!"

        val executor = Executors.newSingleThreadScheduledExecutor()

        executor.schedule({
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)

        }, 1500, TimeUnit.MILLISECONDS)
    }
}