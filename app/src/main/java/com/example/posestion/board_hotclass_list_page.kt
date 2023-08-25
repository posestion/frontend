package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardHotclassListPageBinding

class board_hotclass_list_page : AppCompatActivity() {
    private lateinit var binding: ActivityBoardHotclassListPageBinding

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
        super.onCreate(savedInstanceState)
        binding = ActivityBoardHotclassListPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews(){
        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        binding.hotclassList.layoutManager = gridLayoutManager
        binding.hotclassList.adapter = hotclass_list_adapter(hotclass)
    }
}