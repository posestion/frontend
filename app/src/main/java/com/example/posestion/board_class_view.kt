package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardClassViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class board_class_view : AppCompatActivity() {
    private lateinit var binding: ActivityBoardClassViewBinding
    private val classview_item = listOf(
        classview_item(R.drawable.rectangle_142),
        classview_item(R.drawable.creation_page_imageview),
        classview_item(R.drawable.rectangle_142)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardClassViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val token = MyApplication.user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)

        val call: Call<RetrofitClient.classdetailpage> = apiservice.classdetailpage(token)
        call.enqueue(object : Callback<RetrofitClient.classdetailpage> {
            override fun onResponse(call: Call<RetrofitClient.classdetailpage>, response: Response<RetrofitClient.classdetailpage>) {
                if (response.isSuccessful) {
                    val drawer = response.body()
                    val dataList1: List<RetrofitClient.classdetailcontent> = drawer?.result?.classdetailcontent ?: emptyList()
                    val dataList2: List<RetrofitClient.classdetailimage> = drawer?.result?.classdetailimage ?: emptyList()
                    val dataList3: List<RetrofitClient.classdetailreviews> = drawer?.result?.classdetailreviews ?: emptyList()
                    val dataList4: List<RetrofitClient.classdetaildibs> = drawer?.result?.classdetaildibs ?: emptyList()
                    initializeViews()
                    binding.boardClassViewViewpager.adapter = board_class_view_viewpager_adapter(dataList2)
                    binding.boardClassViewReviewList.adapter = board_class_view_review_adapter(dataList3)
                    binding.boardClassViewReviewCount.text = dataList3.size.toString()
                    // API 요청이 성공한 경우
                    // 응답 데이터를 처리하세요.
                } else {
                    // API 요청이 실패한 경우
                    // 오류 처리를 수행하세요.
                    println("Error")
                }
            }

            override fun onFailure(call: Call<RetrofitClient.classdetailpage>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리를 수행하세요.
                println("Error")
            }
        })

        initializeViews()
    }

    private fun initializeViews(){

        binding.boardClassViewViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.boardClassViewReviewList.layoutManager = LinearLayoutManager(this)
    }
}