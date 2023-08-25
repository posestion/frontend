package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.posestion.databinding.ActivityBoardClassViewBinding

class board_class_view : AppCompatActivity() {
    private lateinit var binding: ActivityBoardClassViewBinding
    private val classview_item = listOf(
        classview_item(R.drawable.rectangle_142),
        classview_item(R.drawable.creation_page_imageview),
        classview_item(R.drawable.rectangle_142)
    )
    private val review = listOf(
        review("이**","2022.05.02","설명을 너무 잘해놓으셨고 이해하기 좋아요!"),
        review("김**","2023.01.03","정리가 너무 잘 되어있고 그대로 하니까 인생사진 나왔어요"),
        review("박**","2023.05.01","너무 필요한 정보들만 쏙쏙 뽑아서 하나에 넣어두셨어요"),
        review("이**","2022.05.02","설명을 너무 잘해놓으셨고 이해하기 좋아요!"),
        review("김**","2023.01.03","정리가 너무 잘 되어있고 그대로 하니까 인생사진 나왔어요"),
        review("박**","2023.05.01","너무 필요한 정보들만 쏙쏙 뽑아서 하나에 넣어두셨어요"),
        review("이**","2022.05.02","설명을 너무 잘해놓으셨고 이해하기 좋아요!"),
        review("김**","2023.01.03","정리가 너무 잘 되어있고 그대로 하니까 인생사진 나왔어요"),
        review("박**","2023.05.01","너무 필요한 정보들만 쏙쏙 뽑아서 하나에 넣어두셨어요"),
        review("이**","2022.05.02","설명을 너무 잘해놓으셨고 이해하기 좋아요!"),
        review("김**","2023.01.03","정리가 너무 잘 되어있고 그대로 하니까 인생사진 나왔어요"),
        review("박**","2023.05.01","너무 필요한 정보들만 쏙쏙 뽑아서 하나에 넣어두셨어요")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardClassViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews(){
        binding.boardClassViewViewpager.adapter = board_class_view_viewpager_adapter(classview_item as ArrayList<classview_item>)
        binding.boardClassViewViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.boardClassViewReviewList.layoutManager = LinearLayoutManager(this)
        binding.boardClassViewReviewList.adapter = board_class_view_review_adapter(review)
        binding.boardClassViewReviewCount.text = review.size.toString()
    }
}