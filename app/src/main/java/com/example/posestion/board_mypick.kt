package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardMypickBinding

class board_mypick : AppCompatActivity() {
    private lateinit var binding: ActivityBoardMypickBinding
    private val hotclass = listOf(
        hotclass(false,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_142),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_62),
        hotclass(false,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_62),
        hotclass(false,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_142),
        hotclass(false,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_142),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_62),
        hotclass(true,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_62),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_142),
        hotclass(true,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_142),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_62),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardMypickBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeViews()
    }
    private fun initializeViews(){
        binding.boardMypickList.layoutManager = LinearLayoutManager(this)
        binding.boardMypickList.adapter = board_mypick_adapter(hotclass)
    }
}