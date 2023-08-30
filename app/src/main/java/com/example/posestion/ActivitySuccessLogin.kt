package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.adlist
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivitySuccessLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.posestion.MyApplication.Companion.classlist
import com.example.posestion.MyApplication.Companion.contentslist
import com.example.posestion.MyApplication.Companion.homeclasslist
import com.example.posestion.MyApplication.Companion.homehotclasslist
import com.example.posestion.MyApplication.Companion.homeposelist
import com.example.posestion.MyApplication.Companion.homestarclasslist
import com.example.posestion.MyApplication.Companion.poselist

class ActivitySuccessLogin : AppCompatActivity() {

    private val binding: ActivitySuccessLoginBinding by lazy { ActivitySuccessLoginBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val editor = user.edit()
    private var token = ""
    private var OK = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        token = user.getString("jwt","").toString()

        val call = RetrofitObject.getRetrofitService.mypage(token)
        call.enqueue(object : Callback<RetrofitClient.Responsemypage> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypage>, response: Response<RetrofitClient.Responsemypage>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if (response != null) {
                        if (response.isSuccess) {
                            val mypage = response.result[0]
                            editor.putInt("expert", mypage.expert)
                            editor.putString("profileimage", mypage.profile)
                            editor.putString("nick", mypage.nick)
                            editor.putInt("post", mypage.post)
                            editor.putInt("following", mypage.following)
                            editor.putInt("follower", mypage.follower)
                            editor.putString("intro", mypage.introduction)
                            editor.apply()

                            if(mypage.expert != 0) {
                                if(mypage.mypageclass != null){
                                    classlist = mypage.mypageclass
                                }
                            }
                            if(mypage.myContent != null){
                                contentslist = mypage.myContent
                            }
                            if(mypage.poseDrawer != null){
                                poselist = mypage.poseDrawer
                            }
                            OK++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypage>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        val callhot = RetrofitObject.getRetrofitService.homehotclass(token)
        callhot.enqueue(object : Callback<RetrofitClient.Responsehomehotclass> {
            override fun onResponse(call: Call<RetrofitClient.Responsehomehotclass>, response: Response<RetrofitClient.Responsehomehotclass>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            if(response.result != null){
                                homehotclasslist = response.result
                            }
                            OK++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsehomehotclass>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        val callpose = RetrofitObject.getRetrofitService.homepose(token)
        callpose.enqueue(object : Callback<RetrofitClient.Responsehomepose> {
            override fun onResponse(call: Call<RetrofitClient.Responsehomepose>, response: Response<RetrofitClient.Responsehomepose>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            if(response.result != null){
                                homeposelist = response.result
                            }
                            OK++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsehomepose>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        val callclass = RetrofitObject.getRetrofitService.homemyclass(token)
        callclass.enqueue(object : Callback<RetrofitClient.Responsemypageclass> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypageclass>, response: Response<RetrofitClient.Responsemypageclass>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            if(response.result != null){
                               homeclasslist = response.result
                            }
                            OK++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypageclass>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        val callstar = RetrofitObject.getRetrofitService.homestarclass(token)
        callstar.enqueue(object : Callback<RetrofitClient.Responsemypageclass> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypageclass>, response: Response<RetrofitClient.Responsemypageclass>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            if(response.result != null){
                                homestarclasslist = response.result
                            }
                            OK++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypageclass>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
        val callad = RetrofitObject.getRetrofitService.getad(token)
        callad.enqueue(object : Callback<RetrofitClient.ResponseHomeAd> {
            override fun onResponse(call: Call<RetrofitClient.ResponseHomeAd>, response: Response<RetrofitClient.ResponseHomeAd>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if (response != null) {
                        if (response.isSuccess) {
                            if(response.result != null){
                                adlist = response.result
                            }
                            OK++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.ResponseHomeAd>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        if(OK == 6){
            val intent = Intent(this, ActivityMain::class.java)
            startActivity(intent)
            finish()
        }
    }
}