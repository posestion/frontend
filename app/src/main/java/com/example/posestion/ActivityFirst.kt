package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityFirst : AppCompatActivity() , View.OnTouchListener {

    private val user = MyApplication.user
    private var id = ""
    private var pw = ""

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if(user.getBoolean("autologin", false)){
                id = user.getString("id", "").toString()
                pw = user.getString("pw", "").toString()
                if(id == "" || pw == ""){
                    val intent = Intent(this, ActivityLogin::class.java)
                    startActivity(intent)
                }
                else{
                    val call = RetrofitObject.getRetrofitService.login(RetrofitClient.Requestlogin(id, pw))
                    call.enqueue(object : Callback<RetrofitClient.Responselogin> {
                        override fun onResponse(call: Call<RetrofitClient.Responselogin>, response: Response<RetrofitClient.Responselogin>) {
                            if (response.isSuccessful) {
                                val response = response.body()
                                if(response != null){
                                    if(response.isSuccess){
                                        val auto = user.getBoolean("autologin", false)
                                        if(auto){
                                            val intent = Intent(this@ActivityFirst, loginsuccess::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<RetrofitClient.Responselogin>, t: Throwable) {
                            val errorMessage = "Call Failed: ${t.message}"
                            Log.d("Retrofit", errorMessage)
                        }
                    })
                }
            }else{
                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
            }
            return true
        }
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        this.onBackPressedDispatcher.addCallback(this, callback)

        findViewById<View>(android.R.id.content).setOnTouchListener(this)
    }
}