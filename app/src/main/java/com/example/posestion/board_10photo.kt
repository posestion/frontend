package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityBoard10photoBinding

class board_10photo : AppCompatActivity() {
    private lateinit var binding:ActivityBoard10photoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityBoard10photoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val extras = intent.extras
        val title = extras!!["title"] as String
        val job = extras!!["job"] as String
        val photo = extras!!["photo"] as Int

        binding.board10photoTitle.text = title
        binding.board10PhotoName.text = job
        binding.board10photoImage.setImageResource(photo)
    }
}