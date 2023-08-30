package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.MyApplication.Companion.user
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoardClassHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        val token = user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)
        runBlocking {
            launch(Dispatchers.IO) {
                val hotClassResponse: Response<RetrofitClient.boardHotClass> = apiservice.boardhotclass(token).execute()
                if (hotClassResponse.isSuccessful) {
                    val hotClassDrawer = hotClassResponse.body()
                    val hotClassDataList: List<RetrofitClient.getHotClass> = hotClassDrawer?.result ?: emptyList()
                    // hotClassDataList을 사용하여 작업 수행
                    binding.boardClassHomeHotclassList.adapter = board_class_home_hotclass_adapter(hotClassDataList)
                } else {
                    // hotClassResponse가 실패한 경우 처리
                }

                val dibsClassResponse: Response<RetrofitClient.boardDibsClass> = apiservice.boarddibsclass(token).execute()
                if (dibsClassResponse.isSuccessful) {
                    val dibsClassDrawer = dibsClassResponse.body()
                    val dibsClassDataList: List<RetrofitClient.getDibsClass> = dibsClassDrawer?.result ?: emptyList()
                    // dibsClassDataList을 사용하여 작업 수행
                    binding.boardClassHomeMypickList.adapter = board_class_home_mypick_adapter(dibsClassDataList)
                } else {
                    // dibsClassResponse가 실패한 경우 처리
                }
            }
        }

// 여기서는 비동기 호출이 완료되었습니다.
// 다음 작업을 수행할 수 있습니다.
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

    }
}