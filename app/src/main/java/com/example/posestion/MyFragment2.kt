package com.example.posestion

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFragment2 : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var viewModel: MyCustomViewModel
    private lateinit var rvAdapter: MyRecyclerViewAdapter
    private lateinit var retrofitServiceWithToken: RetrofitAPI
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        viewModel = ViewModelProvider(requireActivity()).get(MyCustomViewModel::class.java)

        val token = MyApplication.user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedViewModel을 가져와서 검색 결과 데이터를 가져옴
        sharedViewModel.searchResults.observe(viewLifecycleOwner, { searchResults ->
            Log.d("Retrofit28", searchResults.toString())
            customAdapter.setData(searchResults.map { poseSearch ->
                RetrofitClient.PoseGetage(
                    id = poseSearch.id,
                    poseImage = poseSearch.poseImage,
                    title = poseSearch.title.toString(),
                    content = poseSearch.content.toString(),
                    tagnames = poseSearch.tagname,
                    date = poseSearch.date,
                    poseId=poseSearch.poseId,
                    userId = poseSearch.userId,
                    view = poseSearch.view
                )
            })
        })

        sharedViewModel.filterDates.observe(viewLifecycleOwner, { filterDates ->
            Log.d("Retrofit29", filterDates.toString())
            customAdapter.setData(filterDates.map { poseFilter ->
                RetrofitClient.PoseGetage(
                    id = poseFilter.id,
                    poseImage = poseFilter.poseImage,
                    title = poseFilter.title.toString(),
                    content = poseFilter.content.toString(),
                    tagnames = poseFilter.tagnames,
                    date = poseFilter.date,
                    poseId=poseFilter.poseid,
                    userId = poseFilter.userId,
                    view = poseFilter.view
                )
            })
        })

        sharedViewModel.filterPopulates.observe(viewLifecycleOwner, { filterPopulates ->
            Log.d("Retrofit29-2", filterPopulates.toString())
            customAdapter.setData(filterPopulates.map { poseFilter ->
                RetrofitClient.PoseGetage(
                    id = poseFilter.id,
                    poseImage = poseFilter.poseImage,
                    title = poseFilter.title.toString(),
                    content = poseFilter.content.toString(),
                    tagnames = poseFilter.tagnames,
                    date = poseFilter.date,
                    poseId=poseFilter.poseid,
                    userId = poseFilter.userId,
                    view = poseFilter.view
                )
            })
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.newfragment_2, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        customAdapter = CustomAdapter(
            sharedViewModel,
            retrofitServiceWithToken,
            viewLifecycleOwner, // 수정: viewLifecycleOwner 사용
            showLargeImageDialog
        )
        recyclerView.adapter = customAdapter

        retrofitServiceWithToken.posegetage().enqueue(object : Callback<RetrofitClient.PoseGetageResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseGetageResponse>,
                response: Response<RetrofitClient.PoseGetageResponse>
            ) {
                if (response.isSuccessful) {
                    val poseGetageResponse = response.body()
                    val dataList: List<RetrofitClient.PoseGetage> = poseGetageResponse?.result ?: emptyList()

                    customAdapter.setData(dataList) // RecyclerView 데이터 업데이트
                } else {
                    // 서버 응답이 실패한 경우 처리
                }
            }

            override fun onFailure(call: Call<RetrofitClient.PoseGetageResponse>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit20", errorMessage)
            }
        })

        return rootView
    }

    private val showLargeImageDialog: (String, String, String, Int,List<String>?) -> Unit =
        { imageUrl, title, content, imageId, tagnames ->
            // 이미지를 크게 보여주는 AlertDialog를 표시하는 로직을 작성
            val inflater = LayoutInflater.from(requireContext())
            val dialogView = inflater.inflate(R.layout.dialog_large_image, null)

            // 커스텀 레이아웃의 이미지뷰를 찾습니다.
            val largeImageView = dialogView.findViewById<ImageView>(R.id.largeImageView)
            Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .into(largeImageView)

            val titleTextView = dialogView.findViewById<TextView>(R.id.textView3)
            titleTextView.text = title

            val contentTextView = dialogView.findViewById<TextView>(R.id.textView7)
            contentTextView.text = content

            val tagTextView = dialogView.findViewById<TextView>(R.id.textView13)

            if (tagTextView.text!= null) {
                val tagsText = tagnames?.filterNotNull()?.joinToString(" ") { tag -> "#$tag" } ?: ""
                tagTextView.text = tagsText
            } else {
                tagTextView.text = ""
            }

            // AlertDialog를 생성합니다.
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(dialogView)

            // AlertDialog를 표시합니다.
            val alertDialog = builder.create()
            alertDialog.show()

            val imageButton1 = dialogView.findViewById<ImageButton>(R.id.imageButton)
            imageButton1.setOnClickListener {
                showPopupMenu(it,imageId,imageUrl)
            }
        }

        private fun showPopupMenu(view: View, imageId: Int, imageUrl:String) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_second)
        popupMenu.setOnMenuItemClickListener { item ->
            // 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
            when (item.itemId) {
                R.id.item1 -> {
                    Log.d("Popup", "Adding pose with imageId: $imageId")

                    val requestBody = RetrofitClient.PoseRequestBody(imageId)
                    retrofitServiceWithToken.posebasket(requestBody).enqueue(object :
                        Callback<RetrofitClient.PoseBasket> {
                        override fun onResponse(
                            call: Call<RetrofitClient.PoseBasket>,
                            response: Response<RetrofitClient.PoseBasket>
                        ) {
                            Log.d("Retrofit24", response.toString())
                            if (response.isSuccessful) {
                                viewModel.addImageId(imageId)
                                viewModel.addImageUrl(imageId, imageUrl)
                                Log.d("Retrofit21", viewModel.addImageUrl(imageId, imageUrl).toString())
                            }
                        }
                        override fun onFailure(
                            call: Call<RetrofitClient.PoseBasket>,
                            t: Throwable
                        ) {
                            val errorMessage = "Call Failed: ${t.message}"
                            Log.d("Retrofit23", errorMessage)
                        }
                    })

                    Toast.makeText(requireContext(), "포즈가 장바구니에 담겼어요!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item2 -> {
                    // "순서바꾸기" 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
                    // 여기서 원하는 동작을 구현하면 됩니다.
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
