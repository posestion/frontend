package com.example.posestion

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityMainBinding
import com.example.posestion.databinding.PoseshopBinding
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PoseshopFilter : AppCompatActivity() {
    private lateinit var binding: PoseshopBinding
    private lateinit var retrofitServiceWithToken: RetrofitAPI

    private val bindings: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PoseshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = MyApplication.user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())

        binding.radioGraphics.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton_low -> {
                    // "추천순" 라디오 버튼 선택 시의 동작
                    // API 호출 및 응답 처리
                }
                R.id.radioButton_medium -> {
                    // "인기순" 라디오 버튼 선택 시의 동작
                    // API 호출 및 응답 처리
                }
                R.id.radioButton_high -> {
                    // "최신순" 라디오 버튼 선택 시의 동작
                    prcssFilterByLatest()
                }
            }
        }
        binding.buttonLoad.setOnClickListener {
            val fragment = PoseshopMainFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
        }
        binding.edittext.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event != null) {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode === KeyEvent.KEYCODE_ENTER) {
                        //키패드 내리기
                        val imm: InputMethodManager =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.edittext.getWindowToken(), 0)

                        //처리
                        //prcss()
                        return true
                    }
                }
                return false
            }
        })

    }

    private fun prcssFilterByLatest() {
        // "최신순" 라디오 버튼 선택 시의 동작 처리
        val call = retrofitServiceWithToken.posefilterdate()

        call.enqueue(object : Callback<RetrofitClient.PoseFilterdateResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseFilterdateResponse>,
                response: Response<RetrofitClient.PoseFilterdateResponse>
            ) {
                // 응답 처리
                if (response.isSuccessful) {
                    val result = response.body()?.result
                    Log.d("RetrofitSearch6", result.toString())
                    if (result != null) {
                        val bundle = Bundle()
                        bundle.putParcelableArrayList("filterdates", result as ArrayList<out Parcelable>)
                        val fragment2 = MyFragment2()
                        fragment2.arguments = bundle

                        val fragment3 = MyFragment3()
                        val bunddle=Bundle()
                        bunddle.putString("test1","Teeest")

                        fragment3.arguments=bunddle

                        fragment3.arguments=bunddle
                        Log.d("RetrofitSearch5", "Fragment3 arguments: ${fragment3.arguments}")

                    }
                }
            }

            override fun onFailure(
                call: Call<RetrofitClient.PoseFilterdateResponse>,
                t: Throwable
            ) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit25", errorMessage)
            }
        })
    }
}
