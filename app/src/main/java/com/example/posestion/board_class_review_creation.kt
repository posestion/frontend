package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityBoardClassReviewCreationBinding

class board_class_review_creation : AppCompatActivity() {
    private lateinit var binding: ActivityBoardClassReviewCreationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardClassReviewCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}