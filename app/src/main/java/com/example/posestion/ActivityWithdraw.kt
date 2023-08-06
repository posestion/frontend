package com.example.posestion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.posestion.databinding.ActivityWithdrawBinding

class ActivityWithdraw : AppCompatActivity() {

    private val binding: ActivityWithdrawBinding by lazy { ActivityWithdrawBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}