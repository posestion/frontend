package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardBasicClasspageBinding

class board_basic_classpage : AppCompatActivity() {
    private lateinit var binding: ActivityBoardBasicClasspageBinding

    private val basic_myclass = listOf(
        basic_myclass("인물 사진 잘 찍는 법 종결합니다"),
        basic_myclass("똥손 탈출하는 전신샷 잘 찍는법"),
        basic_myclass("인물 사진 잘 찍는 법 종결합니다"),
        basic_myclass("똥손 탈출하는 전신샷 잘 찍는법")
    )
    private val basic_mypick = listOf(
        basic_mypick("인물 사진 잘 찍는 법 종결합니다"),
        basic_mypick("똥손 탈출하는 전신샷 잘 찍는법"),
        basic_mypick("인물 사진 잘 찍는 법 종결합니다"),
        basic_mypick("똥손 탈출하는 전신샷 잘 찍는법")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardBasicClasspageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews(){
        val gridLayoutManager1 = GridLayoutManager(applicationContext, 2)
        val gridLayoutManager2 = GridLayoutManager(applicationContext, 2)
        gridLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        gridLayoutManager2.orientation = LinearLayoutManager.VERTICAL
        binding.basicMyclassList.layoutManager = gridLayoutManager1
        binding.basicMyclassList.adapter = basic_myclass_adapter(basic_myclass)
        binding.basicMypickList.layoutManager = gridLayoutManager2
        binding.basicMypickList.adapter =basic_mypick_adapter(basic_mypick)
    }
}