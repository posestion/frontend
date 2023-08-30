package com.example.posestion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentBoardHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class board_home : Fragment() {
    private lateinit var binding: FragmentBoardHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardHomeBinding.inflate(layoutInflater)

        val token = MyApplication.user.getString("jwt", "").toString()
        val apiservice = RetrofitObject.getRetrofitServiceWithToken(token)

        runBlocking {
            launch(Dispatchers.IO) {

        val boardClassResponse: Response<RetrofitClient.boardClass> = apiservice.boardclass(token).execute()
        if (boardClassResponse.isSuccessful) {
            val boardClassDrawer = boardClassResponse.body()
            val boardClassDataList: List<RetrofitClient.getclass> = boardClassDrawer?.result ?: emptyList()
            // hotClassDataList을 사용하여 작업 수행
            binding.boardHomeClassList.adapter = home_hotclass_list_adapter(boardClassDataList)
        } else {
            // hotClassResponse가 실패한 경우 처리
        }
        val board10photoResponse: Response<RetrofitClient.AllTensPhoto> = apiservice.alltensphoto(token).execute()
        if (board10photoResponse.isSuccessful) {
            val board10photoDrawer = board10photoResponse.body()
            val board10photoDataList: List<RetrofitClient.TensPhoto> = board10photoDrawer?.result ?: emptyList()
            // hotClassDataList을 사용하여 작업 수행
            binding.boardHome10photoList.adapter = home_10photo_adapter(board10photoDataList)
        } else {
            // hotClassResponse가 실패한 경우 처리
        }
        val boardlistResponse: Response<RetrofitClient.boardPhotowell> = apiservice.boardphotowell(token).execute()
        if (boardlistResponse.isSuccessful) {
            val boardlistDrawer = boardlistResponse.body()
            val boardlistDataList: List<RetrofitClient.photowell> = boardlistDrawer?.result ?: emptyList()
            // hotClassDataList을 사용하여 작업 수행
            binding.boardHomeListpageList.adapter = home_listpage_adapter(boardlistDataList)
        } else {
            // hotClassResponse가 실패한 경우 처리
        }}}
        initializeViews()

        binding.boardHomeListpageBtn.setOnClickListener {
            val intent = Intent(activity,board_listpage::class.java)
            startActivity(intent)
        }
        binding.boardHomeClassBtn.setOnClickListener {
            val intent = Intent(requireContext(),board_class_home::class.java)
            startActivity(intent)
        }

        return binding.root
    }
    fun initializeViews(){
        val LinearLayoutManager0 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        val LinearLayoutManager1 = LinearLayoutManager(requireContext())
        val LinearLayoutManager2 = GridLayoutManager(requireContext(),2)
        binding.boardHome10photoList.layoutManager = LinearLayoutManager0

        binding.boardHomeListpageList.layoutManager = LinearLayoutManager1

        binding.boardHomeClassList.layoutManager = LinearLayoutManager2


    }
}