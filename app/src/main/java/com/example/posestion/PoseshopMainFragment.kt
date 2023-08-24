package com.example.posestion

import android.app.appsearch.SearchResult
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.PoseshopmainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoseshopMainFragment : Fragment() {
    private lateinit var binding: PoseshopmainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MyCustomViewModel
    private lateinit var retrofitServiceWithToken: RetrofitAPI
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
            Log.d("MyRecyclerViewAdapter91", viewModel.addedImageIds.value.toString())
            startActivity(intent)
        }

        binding.filter.setOnClickListener {
            val intent = Intent(requireContext(), PoseshopFilter::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
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
        retrofitServiceWithToken.posesearch(word = query).enqueue(object :
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

                            val bundle = Bundle()
                            bundle.putSerializable("searchResults", ArrayList(searchResults))

                            val fragment2 = MyFragment2()
                            fragment2.arguments = bundle

                            val fragment3 = MyFragment3()
                            fragment3.arguments = bundle
                            Log.d("RetrofitSearch4", "Fragment3 arguments: ${fragment3.arguments}")
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
}