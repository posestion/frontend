package com.example.posestion

import android.app.Activity
import android.app.appsearch.SearchResult
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.PoseshopmainBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder
import java.net.URLEncoder

class PoseshopMainFragment : Fragment() {
    private lateinit var binding: PoseshopmainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MyCustomViewModel
    private lateinit var retrofitServiceWithToken: RetrofitAPI

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val filterActivityResultLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("Retrofit82", result.resultCode.toString())
        Log.d("Retrofit83", AppCompatActivity.RESULT_OK.toString())
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val bundle = result.data?.extras
            Log.d("Retrofit86", bundle.toString())
            val serializable = bundle?.get("filter_dates")
            Log.d("Retrofit88", serializable.toString())
            val serializabled = bundle?.get("filter_populates")
            Log.d("Retrofit88", serializabled.toString())
            if (serializable is ArrayList<*>) {
                val filterDates = serializable.filterIsInstance<RetrofitClient.PoseFilterdate>()
                sharedViewModel.setFilterDates(filterDates)
                Log.d("Retrofit89", filterDates.toString())
            }
            else if(serializabled is ArrayList<*>){
                val filterPopulates = serializabled.filterIsInstance<RetrofitClient.PoseFilterpopular>()
                sharedViewModel.setFilterPopulates(filterPopulates)
                Log.d("Retrofit89", filterPopulates.toString())
            }
        }
    }

    private val poseshopActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("Retrofit82", result.resultCode.toString())
        Log.d("Retrofit83", AppCompatActivity.RESULT_OK.toString())
        if (result.resultCode == Activity.RESULT_OK) {
            val deletedImageIds = result.data?.getIntegerArrayListExtra("deletedPoseId")
            if (deletedImageIds != null && deletedImageIds.isNotEmpty()) {
                for (imageId in deletedImageIds) {
                    viewModel.deleteImage(imageId)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val token = MyApplication.user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PoseshopmainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MyCustomViewModel::class.java)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receivedValue = activity?.intent?.getStringExtra("key_name")
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.viewpager.apply {
            adapter = MyPagerAdapter(requireContext() as FragmentActivity)
        }

        val tabTitles = listOf("좋아요", "연령별", "HOT")

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.sbutton.setOnClickListener {
            val intent = Intent(requireContext(), PoseShopingactiv::class.java)
            intent.putExtra("key_name", receivedValue)
            intent.putIntegerArrayListExtra("addedImageIds", ArrayList(viewModel.addedImageIds.value))
            val imageUrlsList = viewModel.getAllImageUrls()
            intent.putStringArrayListExtra("addedImageUrls", ArrayList(imageUrlsList))
            Log.d("MyRecyclerViewAdapter91", viewModel.addedImageIds.value.toString())
            Log.d("MyRecyclerViewAdapter95", imageUrlsList.toString())
            poseshopActivityResultLauncher.launch(intent)
        }

        binding.filter.setOnClickListener {
            val intent = Intent(requireContext(), PoseshopFilter::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            filterActivityResultLauncher.launch(intent)
        }

        binding.edittext.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // 엔터 키가 눌렸을 때 하고 싶은 동작을 여기에 작성
                // 예를 들어 검색을 수행하고 로그를 출력하는 등의 작업
                val searchQuery = binding.edittext.text.toString().trim()
                Log.d("RetrofitSearch2", searchQuery)

                // 키패드 내리기
                val imm: InputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edittext.windowToken, 0)

                // 검색어가 비어있지 않은 경우 API 호출
                if (searchQuery.isNotEmpty()) {
                    performSearch(searchQuery)
                    Log.d("RetrofitSearch1", "performSearch called")
                    performSearchHot(searchQuery)
                    Log.d("RetrofitSearch12", "performSearchHot called")
                }

                true
            } else {
                false
            }
        })
    }
    override fun onResume() {
        super.onResume()
        // 제거 로직이 필요 없음
    }
    private var searchResults: List<SearchResult> = emptyList()

    private fun performSearch(query: String) {
        Log.d("RetrofitSearch8", query)
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        Log.d("RetrofitSearch81", encodedQuery)
        val decodedQuery = URLDecoder.decode(encodedQuery, "UTF-8")
        retrofitServiceWithToken.posesearch(word = decodedQuery).enqueue(object :
            Callback<RetrofitClient.PoseSearchResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseSearchResponse>,
                response: Response<RetrofitClient.PoseSearchResponse>
            ) {
                Log.d("RetrofitSearch73", response.toString())
                if (response.isSuccessful) {
                    val poseSearchResponse = response.body()
                    Log.d("RetrofitSearch7", poseSearchResponse.toString())
                    if (poseSearchResponse?.isSuccess == true) {
                        val searchResults: List<RetrofitClient.PoseSearch> =
                            poseSearchResponse.result
                        if (searchResults != null) {
                            Log.d("RetrofitSearch51", "Search results: $searchResults")
                            // 어댑터에 검색 결과를 전달하고 화면을 업데이트
                            sharedViewModel.setSearchResults(searchResults)
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
    private fun performSearchHot(query: String) {
        Log.d("RetrofitSearch8", query)
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val decodedQuery = URLDecoder.decode(encodedQuery, "UTF-8")
        retrofitServiceWithToken.posesearchhot(hot = decodedQuery).enqueue(object :
            Callback<RetrofitClient.PoseSearchHotResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseSearchHotResponse>,
                response: Response<RetrofitClient.PoseSearchHotResponse>
            ) {
                Log.d("RetrofitSearch72", response.toString())
                if (response.isSuccessful) {
                    val poseSearchResponse = response.body()
                    Log.d("RetrofitSearch72", poseSearchResponse.toString())
                    if (poseSearchResponse?.isSuccess == true) {
                        val searchResults: List<RetrofitClient.PoseSearchHot> =
                            poseSearchResponse.result
                        if (searchResults != null) {
                            Log.d("RetrofitSearch52", "Search results: $searchResults")
                            // 어댑터에 검색 결과를 전달하고 화면을 업데이트
                            sharedViewModel.setSearchHotResults(searchResults)
                        } else {
                            Log.d("RetrofitSearch6", "Search results are null")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient.PoseSearchHotResponse>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("RetrofitSearch522", errorMessage)
            }
        })
    }
}
