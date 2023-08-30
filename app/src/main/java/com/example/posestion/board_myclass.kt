package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardMyclassBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class board_myclass : AppCompatActivity() {
    private lateinit var binding: ActivityBoardMyclassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardMyclassBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val token = MyApplication.user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)
        val call: Call<RetrofitClient.classmyclass> = apiservice.classdrawermyclass(token)
        call.enqueue(object : Callback<RetrofitClient.classmyclass> {
            override fun onResponse(call: Call<RetrofitClient.classmyclass>, response: Response<RetrofitClient.classmyclass>) {
                if (response.isSuccessful) {
                    val drawer = response.body()
                    val dataList1: List<RetrofitClient.listenmyclass> = drawer?.result ?: emptyList()
                    initializeViews()
                    binding.boardMyclassList.adapter = board_myclass_adapter(dataList1)
                    // API 요청이 성공한 경우
                    // 응답 데이터를 처리하세요.
                } else {
                    // API 요청이 실패한 경우
                    // 오류 처리를 수행하세요.
                }
            }

            override fun onFailure(call: Call<RetrofitClient.classmyclass>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리를 수행하세요.
            }
        })
    }
    private fun initializeViews(){
        binding.boardMyclassList.layoutManager = LinearLayoutManager(this)

    }
}