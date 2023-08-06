package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityClassBinding

class ActivityClass : AppCompatActivity() {

    private val binding: ActivityClassBinding by lazy { ActivityClassBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
    }
}