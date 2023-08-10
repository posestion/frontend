package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardListpageBinding

class board_listpage : AppCompatActivity() {
    private lateinit var binding: ActivityBoardListpageBinding
    private val listpage = listOf(
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!",false, 3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardListpageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews(){
        binding.listpageList.layoutManager = LinearLayoutManager(this)
        binding.listpageList.adapter = listpage_adapter(listpage)
    }
}