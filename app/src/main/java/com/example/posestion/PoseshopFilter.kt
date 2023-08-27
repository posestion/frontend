package com.example.posestion

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.PoseshopBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class PoseshopFilter : AppCompatActivity() {
    private lateinit var binding: PoseshopBinding
    private lateinit var retrofitServiceWithToken: RetrofitAPI
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var filterViewModel: PoseshopFilterSearchViewModel

    object SharedGlobals {
        var filterDatesBundle: Bundle? = null
        var filterPopulatesBundle: Bundle? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PoseshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        filterViewModel= ViewModelProvider(this).get(PoseshopFilterSearchViewModel::class.java)

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
                    prcssFilterByMedium()
                }
                R.id.radioButton_high -> {
                    // "최신순" 라디오 버튼 선택 시의 동작
                    prcssFilterByLatest()
                }
            }
        }
        binding.buttonLoad.setOnClickListener {
            val resultIntent = Intent()
            if(SharedGlobals.filterDatesBundle!=null)
            {
                resultIntent.putExtras(SharedGlobals.filterDatesBundle ?: Bundle()) // 이 부분에서 Bundle이 null일 경우 빈 Bundle을 사용하도록 합니다.
                Log.d("Retrofit28", resultIntent.toString())
                Log.d("Retrofit28", SharedGlobals.filterDatesBundle.toString())
            }
            else if(SharedGlobals.filterPopulatesBundle!=null)
            {
                resultIntent.putExtras(SharedGlobals.filterPopulatesBundle ?: Bundle()) // 이 부분에서 Bundle이 null일 경우 빈 Bundle을 사용하도록 합니다.
                Log.d("Retrofit28-2", resultIntent.toString())
                Log.d("Retrofit28-2", SharedGlobals.filterPopulatesBundle.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        binding.edittext.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event != null) {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode === KeyEvent.KEYCODE_ENTER) {
                        val searchQuery = binding.edittext.text.toString().trim()
                        val imm: InputMethodManager =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.edittext.getWindowToken(), 0)

                        // 검색어가 비어있지 않은 경우 API 호출
                        if (searchQuery.isNotEmpty()) {
                            performSearch(searchQuery)
                            Log.d("RetrofitSearch1", "performSearch called")
                        }
                        return true
                    }
                }
                return false
            }
        })

        binding.radioGraphics4.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton_1 -> {
                    // "포함" 라디오 버튼 선택 시의 동작
                    filterResults(true)
                }
                R.id.radioButton_2 -> {
                    // "제외" 라디오 버튼 선택 시의 동작
                    filterResults(false)
                }
            }
        }

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
                    Log.d("RetrofitSearch7", result.toString())
                    if (result != null) {
                        val result = response.body()?.result
                        if (result != null) {
                            val bundle = Bundle()
                            bundle.putSerializable("filter_dates", ArrayList(result))

                            Log.d("Retrofit29", bundle.toString())
                            SharedGlobals.filterDatesBundle = bundle
                        }
                        SharedGlobals.filterPopulatesBundle = null
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

    private fun prcssFilterByMedium() {
        // "인기순" 라디오 버튼 선택 시의 동작 처리
        val call = retrofitServiceWithToken.posefilterpopular()

        call.enqueue(object : Callback<RetrofitClient.PoseFilterpopularResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseFilterpopularResponse>,
                response: Response<RetrofitClient.PoseFilterpopularResponse>
            ) {
                // 응답 처리
                if (response.isSuccessful) {
                    val resulted = response.body()?.result
                    Log.d("RetrofitSearch6", resulted.toString())
                    if (resulted != null) {
                        val bundle = Bundle()
                        bundle.putSerializable("filter_populates", ArrayList(resulted))

                        Log.d("Retrofit29", bundle.toString())
                        SharedGlobals.filterPopulatesBundle = bundle
                    }
                    SharedGlobals.filterDatesBundle = null
                }
            }

            override fun onFailure(
                call: Call<RetrofitClient.PoseFilterpopularResponse>,
                t: Throwable
            ) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit25", errorMessage)
            }
        })
    }

    private fun performSearch(query: String) {
        Log.d("RetrofitSearch8", query)
        val encodedQuery = URLEncoder.encode(query, "utf-8")
        retrofitServiceWithToken.posesearch(word = encodedQuery).enqueue(object :
            Callback<RetrofitClient.PoseSearchResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseSearchResponse>,
                response: Response<RetrofitClient.PoseSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val poseSearchResponse = response.body()
                    Log.d("RetrofitSearch7", poseSearchResponse.toString())
                    if (poseSearchResponse?.isSuccess == true) {
                        val searchResults: List<RetrofitClient.PoseSearch> =
                            poseSearchResponse.result
                        if (searchResults != null) {
                            Log.d("RetrofitSearch5", "Search results: $searchResults")
                            // 어댑터에 검색 결과를 전달하고 화면을 업데이트
                            filterViewModel.setSearchResults(searchResults)
                        } else {
                            Log.d("RetrofitSearch6", "Search results are null")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient.PoseSearchResponse>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("RetrofitSearch", errorMessage)
            }
        })
    }

    private fun filterResults(include: Boolean) {
    }
}

