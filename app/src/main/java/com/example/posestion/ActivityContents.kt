package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityContentsBinding

class ActivityContents : AppCompatActivity() {

    private val binding: ActivityContentsBinding by lazy { ActivityContentsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents)
    }
}