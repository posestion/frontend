package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivitySuccessLoginBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ActivitySuccessLogin : AppCompatActivity() {

    private val binding: ActivitySuccessLoginBinding by lazy { ActivitySuccessLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val executor = Executors.newSingleThreadScheduledExecutor()

        executor.schedule({
            val intent = Intent(this, ActivityMain::class.java)
            startActivity(intent)

        }, 500, TimeUnit.MILLISECONDS)
    }
}