package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posestion.databinding.ActivityBoardClassReviewCreationBinding

class board_class_review_creation : AppCompatActivity() {
    private lateinit var binding: ActivityBoardClassReviewCreationBinding
    var starcount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardClassReviewCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardClassReviewCreationStar1.setOnClickListener {
            starcount = 1
            star(starcount)
        }
        binding.boardClassReviewCreationStar2.setOnClickListener {
            starcount = 2
            star(starcount)
        }
        binding.boardClassReviewCreationStar3.setOnClickListener {
            starcount = 3
            star(starcount)
        }
        binding.boardClassReviewCreationStar4.setOnClickListener {
            starcount = 4
            star(starcount)
        }
        binding.boardClassReviewCreationStar5.setOnClickListener {
            starcount = 5
            star(starcount)
        }
    }
    private fun star(starcount:Int){
        val starResource_full = R.drawable.class_view_review_full_star
        val starResource_empty = R.drawable.class_view_review_empty_star
        if (starcount == 1) {
            binding.boardClassReviewCreationStar1.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar2.setImageResource(starResource_empty)
            binding.boardClassReviewCreationStar3.setImageResource(starResource_empty)
            binding.boardClassReviewCreationStar4.setImageResource(starResource_empty)
            binding.boardClassReviewCreationStar5.setImageResource(starResource_empty)
        }
        else if(starcount == 2){
            binding.boardClassReviewCreationStar1.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar2.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar3.setImageResource(starResource_empty)
            binding.boardClassReviewCreationStar4.setImageResource(starResource_empty)
            binding.boardClassReviewCreationStar5.setImageResource(starResource_empty)
        }
        else if(starcount == 3){
            binding.boardClassReviewCreationStar1.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar2.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar3.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar4.setImageResource(starResource_empty)
            binding.boardClassReviewCreationStar5.setImageResource(starResource_empty)
        }
        else if(starcount == 4){
            binding.boardClassReviewCreationStar1.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar2.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar3.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar4.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar5.setImageResource(starResource_empty)
        }
        else if(starcount == 5){
            binding.boardClassReviewCreationStar1.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar2.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar3.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar4.setImageResource(starResource_full)
            binding.boardClassReviewCreationStar5.setImageResource(starResource_full)
        }
    }
}