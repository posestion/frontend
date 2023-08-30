package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardHotclassListPageBinding

class board_hotclass_list_page : AppCompatActivity() {
    private lateinit var binding: ActivityBoardHotclassListPageBinding

    private val hotclass = listOf<RetrofitClient.hotclass>()

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