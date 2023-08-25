package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivitySuccessLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.posestion.MyApplication.Companion.classlist

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
                        Log.d("Retrofit", response.message)
                        if (response.isSuccess) {
                            val mypage = response.result[0]
                            editor.putInt("expert", mypage.expert)
                            editor.putString("profileimage", mypage.profile)
                            editor.putString("nick", mypage.nick)
                            editor.putInt("post", mypage.post)
                            editor.putInt("following", mypage.following)
                            editor.putInt("follower", mypage.follower)
                            editor.apply()

                            if(mypage.expert != 0) {
                                //내가 올린 강의
                                val call2 =
                                    RetrofitObject.getRetrofitService.myclass(token, mypage.nick)
                                call2.enqueue(object : Callback<RetrofitClient.ResponsemyClass> {
                                    override fun onResponse(
                                        call: Call<RetrofitClient.ResponsemyClass>,
                                        response: Response<RetrofitClient.ResponsemyClass>
                                    ) {
                                        if (response.isSuccessful) {
                                            val response = response.body()
                                            if (response != null) {
                                                Log.d("Retrofit", response.message)
                                                if (response.isSuccess) {
                                                    classlist = response.result
                                                }
                                            }
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<RetrofitClient.ResponsemyClass>,
                                        t: Throwable
                                    ) {
                                        val errorMessage = "Call Failed: ${t.message}"
                                        Log.d("Retrofit", errorMessage)
                                    }
                                })
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
    }
}