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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivitySuccessLogin : AppCompatActivity() {

    private val binding: ActivitySuccessLoginBinding by lazy { ActivitySuccessLoginBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val editor = user.edit()
    private var token = user.getString("jwt","").toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mypageResponse = RetrofitObject.getRetrofitService.mypage(token).execute()
                val hotclassResponse = RetrofitObject.getRetrofitService.homehotclass(token).execute()
                val poseResponse = RetrofitObject.getRetrofitService.homepose(token).execute()
                val classResponse = RetrofitObject.getRetrofitService.homemyclass(token).execute()
                val starclassResponse = RetrofitObject.getRetrofitService.homestarclass(token).execute()
                val adResponse = RetrofitObject.getRetrofitService.getad(token).execute()

                if (!mypageResponse.isSuccessful) {
                    Log.e("Retrofit", "Mypage Request Failed: ${mypageResponse.code()} ${mypageResponse.message()}")
                }

                if (!hotclassResponse.isSuccessful) {
                    Log.e("Retrofit", "Hotclass Request Failed: ${hotclassResponse.code()} ${hotclassResponse.message()}")
                }

                if (!poseResponse.isSuccessful) {
                    Log.e("Retrofit", "Pose Request Failed: ${poseResponse.code()} ${poseResponse.message()}")
                }

                if (!classResponse.isSuccessful) {
                    Log.e("Retrofit", "Class Request Failed: ${classResponse.code()} ${classResponse.message()}")
                }

                if (!starclassResponse.isSuccessful) {
                    Log.e("Retrofit", "Starclass Request Failed: ${starclassResponse.code()} ${starclassResponse.message()}")
                }

                if (!adResponse.isSuccessful) {
                    Log.e("Retrofit", "Ad Request Failed: ${adResponse.code()} ${adResponse.message()}")
                }

                if (mypageResponse.isSuccessful) {
                    val mypage = mypageResponse.body()?.result?.get(0)
                    mypage?.let {
                        editor.putInt("expert", it.expert)
                        editor.putString("profileimage", it.profile)
                        editor.putString("nick", it.nick)
                        editor.putInt("post", it.post)
                        editor.putInt("following", it.following)
                        editor.putInt("follower", it.follower)
                        editor.putString("intro", it.introduction)
                        editor.apply()

                        if (it.expert != 0 && it.mypageclass != null) {
                            classlist = it.mypageclass
                        }
                    }
                }

                if (hotclassResponse.isSuccessful) {
                    val hotClassList = hotclassResponse.body()?.result
                    hotClassList?.let {
                        homehotclasslist = it
                    }
                }

                if (poseResponse.isSuccessful) {
                    val poseList = poseResponse.body()?.result
                    poseList?.let {
                        homeposelist = it
                    }
                }

                if (classResponse.isSuccessful) {
                    val classList = classResponse.body()?.result
                    classList?.let {
                        homeclasslist = it
                    }
                }

                if (starclassResponse.isSuccessful) {
                    val starClassList = starclassResponse.body()?.result
                    starClassList?.let {
                        homestarclasslist = it
                    }
                }

                if (adResponse.isSuccessful) {
                    val adList = adResponse.body()?.result
                    adList?.let {
                        adlist = it
                    }
                }

                withContext(Dispatchers.Main) {
                    val intent = Intent(this@ActivitySuccessLogin, ActivityMain::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                // 예외 처리 코드 작성
                e.printStackTrace()
                // 에러 상황 처리 등을 수행할 수 있습니다.
            }
        }
    }
}