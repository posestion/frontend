package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardBasicClasspageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class board_basic_classpage : AppCompatActivity() {
    private lateinit var binding: ActivityBoardBasicClasspageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardBasicClasspageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val token = MyApplication.user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)
        val call: Call<RetrofitClient.classdrawer> = apiservice.classdrawer(token)
        call.enqueue(object : Callback<RetrofitClient.classdrawer> {
            override fun onResponse(call: Call<RetrofitClient.classdrawer>, response: Response<RetrofitClient.classdrawer>) {
                if (response.isSuccessful) {
                    val drawer = response.body()
                    val dataList1: List<RetrofitClient.drawermyclass> = drawer?.result?.drawermyclass ?: emptyList()
                    val dataList2: List<RetrofitClient.drawerdibs> = drawer?.result?.drawerdibs ?: emptyList()
                    initializeViews()
                    binding.basicMyclassList.adapter = basic_myclass_adapter(dataList1)
                    binding.basicMypickList.adapter =basic_mypick_adapter(dataList2)
                    // API 요청이 성공한 경우
                    // 응답 데이터를 처리하세요.
                } else {
                    // API 요청이 실패한 경우
                    // 오류 처리를 수행하세요.
                    println("Error")
                }
            }

            override fun onFailure(call: Call<RetrofitClient.classdrawer>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리를 수행하세요.
                println("Error")
            }
        })

    }

    private fun initializeViews(){
        val gridLayoutManager1 = GridLayoutManager(applicationContext, 2)
        val gridLayoutManager2 = GridLayoutManager(applicationContext, 2)
        gridLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        gridLayoutManager2.orientation = LinearLayoutManager.VERTICAL
        binding.basicMyclassList.layoutManager = gridLayoutManager1

        binding.basicMypickList.layoutManager = gridLayoutManager2

    }
}