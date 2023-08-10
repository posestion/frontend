package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityJimBinding

class ActivityJim : AppCompatActivity() {

    private val binding: ActivityJimBinding by lazy { ActivityJimBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jim)
    }
}