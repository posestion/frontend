package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardListpageBinding
import retrofit2.Response

class board_listpage : AppCompatActivity() {
    private lateinit var binding: ActivityBoardListpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardListpageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val token = MyApplication.user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)

        val wdytResponse: Response<RetrofitClient.wdyt> = apiservice.wdyt(token).execute()
        if (wdytResponse.isSuccessful) {
            val wdytDrawer = wdytResponse.body()
            val wdytDataList: List<RetrofitClient.wdytlist> = wdytDrawer?.result ?: emptyList()
            // hotClassDataList을 사용하여 작업 수행
            binding.listpageList.adapter = listpage_adapter(wdytDataList)
        } else {
            // hotClassResponse가 실패한 경우 처리
        }
        initializeViews()


        binding.boardListpageAdd.setOnClickListener {
            val intent = Intent(this,board_creation_page::class.java)
            startActivity(intent)
        }
    }

    private fun initializeViews(){
        binding.listpageList.layoutManager = LinearLayoutManager(this)
    }
}