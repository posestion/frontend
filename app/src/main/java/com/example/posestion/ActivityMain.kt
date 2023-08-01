package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}