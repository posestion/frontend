package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardMasterListBinding

class board_master_list : AppCompatActivity() {

    private lateinit var binding: ActivityBoardMasterListBinding
    private val masterlist = listOf(
        master(1,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(2,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(3,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(4,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(5,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(6,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(7,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(8,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(9,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(10,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(11,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(12,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(13,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(14,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(15,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(16,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(17,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(18,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(19,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(20,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(21,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(22,"빈둥 전문가", "32.3만", "전문 사진작가가 알려주는 강의", false),
        master(23,"루시 전문가", "12.3만", "인스타스타가 알려주는 강의", true),
        master(24,"리오 전문가", "8.2만", "인플루언서가 알려주는 강의", false),
        master(25,"빈둥 전문가", "12.3만", "전문 사진작가가 알려주는 강의", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardMasterListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews(){
        binding.masterList.layoutManager = LinearLayoutManager(this)
        binding.masterList.adapter = master_list_adapter(masterlist)
    }
}