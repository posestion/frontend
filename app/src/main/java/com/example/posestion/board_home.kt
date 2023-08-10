package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.FragmentBoardHomePageBinding

class board_home : AppCompatActivity() {
    private lateinit var binding: FragmentBoardHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentBoardHomePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.boardHomeListpageBtn.setOnClickListener {
            val intent = Intent(this,board_listpage::class.java)
            startActivity(intent)
        }
        binding.masterListBtn.setOnClickListener {
            val intent = Intent(this,board_master_list::class.java)
            startActivity(intent)
        }
        binding.creationPageBtn.setOnClickListener {
            val intent = Intent(this,board_creation_page::class.java)
            startActivity(intent)
        }
        binding.classCreationPageBtn.setOnClickListener {
            val intent = Intent(this,board_class_creation_page::class.java)
            startActivity(intent)
        }
        binding.classReviewBtn.setOnClickListener {
            val intent = Intent(this,board_class_review_creation::class.java)
            startActivity(intent)
        }
        binding.hotclassBtn.setOnClickListener {
            val intent = Intent(this,board_hotclass_list_page::class.java)
            startActivity(intent)
        }
        binding.basicBtn.setOnClickListener {
            val intent = Intent(this,board_basic_classpage::class.java)
            startActivity(intent)
        }
    }
}