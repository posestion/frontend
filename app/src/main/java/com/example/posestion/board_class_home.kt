package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardBasicClasspageBinding
import com.example.posestion.databinding.ActivityBoardClassHomeBinding
import com.example.posestion.databinding.ActivityBoardHotclassListPageBinding
import com.example.posestion.databinding.ActivityBoardMasterListBinding

class board_class_home : AppCompatActivity() {
    private lateinit var binding:ActivityBoardClassHomeBinding

    private val home_masterlist = listOf(
        home_masterlist(1,"빈둥 전문가","전문 사진작가가 알려주는 강의","사진작가"),
        home_masterlist(2,"루시 전문가","인플루언서의 인스타 정복 사진 강의","인플루언서"),
        home_masterlist(3,"사진 전문가","여러분들도 인플루언서가 될 수 있어요!","인플루언서"),
        home_masterlist(4,"빈둥 전문가","전문 사진작가가 알려주는 강의","사진작가"),
        home_masterlist(5,"루시 전문가","인플루언서의 인스타 정복 사진 강의","인플루언서"),
        home_masterlist(6,"사진 전문가","여러분들도 인플루언서가 될 수 있어요!","인플루언서")
    )

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
        binding= ActivityBoardClassHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.boardClassHomeMasterlistBtn.setOnClickListener {
            val intent = Intent(this, board_master_list::class.java)
            startActivity(intent)
        }
        binding.boardClassHomeHotclassBtn.setOnClickListener {
            val intent = Intent(this, board_hotclass_list_page::class.java)
            startActivity(intent)
        }
        binding.boardClassHomeMypickBtn.setOnClickListener {
            val intent = Intent(this, board_basic_classpage::class.java)
            startActivity(intent)
        }

        initializeViews()
    }

    private fun initializeViews(){
        val LinearLayoutManager1 = LinearLayoutManager(this)
        val LinearLayoutManager2 = GridLayoutManager(this,2)
        val LinearLayoutManager3 = GridLayoutManager(this,2)
        binding.boardClassHomeMasterlist.layoutManager = LinearLayoutManager1
        binding.boardClassHomeHotclassList.layoutManager = LinearLayoutManager2
        binding.boardClassHomeMypickList.layoutManager = LinearLayoutManager3
        binding.boardClassHomeMasterlist.adapter = board_class_home_masterlist_adapter(home_masterlist)
        binding.boardClassHomeHotclassList.adapter = board_class_home_hotclass_adapter(hotclass)
        binding.boardClassHomeMypickList.adapter = board_class_home_mypick_adapter(hotclass)
    }
}