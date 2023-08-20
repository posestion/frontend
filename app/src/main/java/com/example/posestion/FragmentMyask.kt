package com.example.posestion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentMyaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMyask : Fragment() {

    private lateinit var binding: FragmentMyaskBinding
    private var token = ""
    private val user = MyApplication.user
    private var asklist = listOf<RetrofitClient.answerx>()
    private var answerlist = listOf<RetrofitClient.answero>()
    private lateinit var recyclerViewanswero: RecyclerView
    private lateinit var recyclerViewanswerx: RecyclerView
    private lateinit var answeroadapter: AdapterMyaskA
    private lateinit var answerxadapter: AdapterMyaskQ

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyaskBinding.inflate(layoutInflater)

        token = user.getString("jwt", "").toString()

        val call = RetrofitObject.getRetrofitService
        call.myask(token)
            .enqueue(object : Callback<RetrofitClient.ResponsemyAsk> {
                override fun onResponse(call: Call<RetrofitClient.ResponsemyAsk>, response: Response<RetrofitClient.ResponsemyAsk>) {
                    if (response.isSuccessful) {
                        Log.d("Retrofit", "success")
                        val result = response.body()
                        if(result != null){
                            answerlist = result.result.answercomplete
                            asklist = result.result.answerincomplete

                            recyclerViewanswero = binding.FmyaskRvA
                            recyclerViewanswerx = binding.FmyaskRvQ
                            answeroadapter = AdapterMyaskA(answerlist)
                            answerxadapter = AdapterMyaskQ(asklist)

                            recyclerViewanswero.layoutManager =
                                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                            recyclerViewanswero.adapter = answeroadapter

                            recyclerViewanswerx.layoutManager =
                                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                            recyclerViewanswerx.adapter = answerxadapter
                            
                            recyclerViewanswero.adapter?.notifyDataSetChanged()
                            recyclerViewanswerx.adapter?.notifyDataSetChanged()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d("Retrofit", "Response Error: $errorBody")
                    }
                }
                override fun onFailure(call: Call<RetrofitClient.ResponsemyAsk>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })

        return binding.root
    }
}