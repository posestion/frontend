package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.posestion.MyApplication.Companion.adlist
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivitySuccessLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.posestion.MyApplication.Companion.classlist
import com.example.posestion.MyApplication.Companion.contentslist
import com.example.posestion.MyApplication.Companion.poselist

class ActivitySuccessLogin : AppCompatActivity() {

    private val binding: ActivitySuccessLoginBinding by lazy { ActivitySuccessLoginBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val editor = user.edit()
    private var token = ""

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

                            val intent = Intent(this@ActivitySuccessLogin, ActivityMain::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypage>, t: Throwable) {
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
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.ResponseHomeAd>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
//        adlist.add(RetrofitClient.HomeAd("a", "https://live.staticflickr.com/65535/51912889686_49cfeda022_o.jpg"))
//        adlist.add(RetrofitClient.HomeAd("b", "https://live.staticflickr.com/65535/51911926992_6515125df4_o.jpg"))
//        adlist.add(RetrofitClient.HomeAd("c", "https://cdn.spotvnews.co.kr/news/photo/202209/549252_767956_254.jpg"))
//        adlist.add(RetrofitClient.HomeAd("d", "https://file2.nocutnews.co.kr/newsroom/image/2022/03/01/202203011752223108_0.jpg"))

    }
}