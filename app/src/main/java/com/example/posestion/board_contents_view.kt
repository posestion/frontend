package com.example.posestion

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityBoardContentsViewBinding

class board_contents_view : AppCompatActivity() {
    private lateinit var binding: ActivityBoardContentsViewBinding
    private var isHeartClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardContentsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val title = extras!!["titletext"] as String
        val contents = extras!!["contentstext"] as String
        isHeartClicked = extras!!["heart"]as Boolean
        var heartcount = extras!!["heartcount"] as Int
        var commentcount = extras!!["commentcount"] as Int

        binding.boardContentsViewHeart.setOnClickListener{
            isHeartClicked = !isHeartClicked
            val heartImageResource =
                if(isHeartClicked){
                    heartcount++
                    binding.boardContentsViewHeartNum.text = heartcount.toString()
                    R.drawable.contents_view_full_heart
            }
            else{
                heartcount--
                    binding.boardContentsViewHeartNum.text = heartcount.toString()
                R.drawable.contents_view_empty_heart
            }

            binding.boardContentsViewHeart.setImageResource(heartImageResource)
        }

        binding.boardContentsViewTitle.text = title
        binding.boardContentsViewContents.text = contents
        binding.boardContentsViewHeartNum.text = heartcount.toString()

        val imageUriString = extras?.getString("imageUriString")
        if (!imageUriString.isNullOrEmpty()) {
            val imageUri = Uri.parse(imageUriString)
            binding.boardContentsViewPhoto.setImageURI(imageUri)
        }
    }
}