package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class Activity_First : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Activity_Start::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}