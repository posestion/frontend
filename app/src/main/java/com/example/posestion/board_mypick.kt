package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardMypickBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class board_mypick : AppCompatActivity() {
    private lateinit var binding: ActivityBoardMypickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardMypickBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val token = MyApplication.user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)
        val call: Call<RetrofitClient.classmydibs> = apiservice.classdrawermydibs(token)
        call.enqueue(object : Callback<RetrofitClient.classmydibs> {
            override fun onResponse(call: Call<RetrofitClient.classmydibs>, response: Response<RetrofitClient.classmydibs>) {
                if (response.isSuccessful) {
                    val drawer = response.body()
                    val dataList1: List<RetrofitClient.mydibs> = drawer?.result ?: emptyList()
                    initializeViews()
                    binding.boardMypickList.adapter = board_mypick_adapter(dataList1)
                    // API 요청이 성공한 경우
                    // 응답 데이터를 처리하세요.
                } else {
                    // API 요청이 실패한 경우
                    // 오류 처리를 수행하세요.
                }
            }

            override fun onFailure(call: Call<RetrofitClient.classmydibs>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리를 수행하세요.
            }
        })
    }
    private fun initializeViews(){
        binding.boardMypickList.layoutManager = LinearLayoutManager(this)

    }
}