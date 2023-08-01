package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityChangeUserBinding

class ActivityChangeUser : AppCompatActivity() {

    private val binding: ActivityChangeUserBinding by lazy { ActivityChangeUserBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}