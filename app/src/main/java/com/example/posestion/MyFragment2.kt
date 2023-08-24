package com.example.posestion

import android.app.AlertDialog
import android.app.appsearch.SearchResult
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentLayout2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFragment2 : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var viewModel: MyCustomViewModel
    private lateinit var rvAdapter: MyRecyclerViewAdapter
    private lateinit var retrofitServiceWithToken: RetrofitAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val token = MyApplication.user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchResults = arguments?.getSerializable("searchResults") as? ArrayList<RetrofitClient.PoseSearch>
        if (searchResults != null) {
            // TODO: searchResults를 활용하여 원하는 작업 수행
        }
    }

    private fun onHeartButtonClick(imageId: Int,imageUrl: String, tagNames: List<String>?) {
        Log.d(imageId.toString(),"bbb")

        retrofitServiceWithToken.poseaddfavorite(imageId).enqueue(object :
            Callback<RetrofitClient.PoseAddfavoriteResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseAddfavoriteResponse>,
                response: Response<RetrofitClient.PoseAddfavoriteResponse>
            ) {
                if (response.isSuccessful) {
                    sharedViewModel.addNewImage(imageId)
                    sharedViewModel.addImageUrl(imageId, imageUrl, tagNames)
                }
            }
            override fun onFailure(call: Call<RetrofitClient.PoseAddfavoriteResponse>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit23", errorMessage)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val RootView = inflater.inflate(R.layout.fragment_layout_2, container, false)

        val binding = FragmentLayout2Binding.inflate(inflater, container, false)

        Log.d("rootview", RootView.toString())
        retrofitServiceWithToken.posegetage()
            .enqueue(object : Callback<RetrofitClient.PoseGetageResponse> {
                override fun onResponse(
                    call: Call<RetrofitClient.PoseGetageResponse>,
                    response: Response<RetrofitClient.PoseGetageResponse>
                ) {
                    if (response.isSuccessful) {
                        val getAgeResponse = response.body()
                        val getAgeDataList = getAgeResponse?.result

                        if (!getAgeDataList.isNullOrEmpty()) {
                            for (i in 0 until 9) {
                                val getage = getAgeDataList[i]

                                Log.d("getage", getage.toString())
                                val imageView: ImageView = RootView.findViewById(
                                    resources.getIdentifier(
                                        "imageView${i + 1}",
                                        "id",
                                        requireContext().packageName
                                    )
                                )
                                val imageButton: ImageButton = RootView.findViewById(
                                    resources.getIdentifier(
                                        "heartButton${i + 1}",
                                        "id",
                                        requireContext().packageName
                                    )
                                )

                                // 이미지 URL 가져와서 이미지뷰에 로드
                                val imageUrl = getage.poseImage
                                Glide.with(requireContext())
                                    .load(imageUrl)
                                    .centerCrop()
                                    .into(imageView)

                                imageView.setOnClickListener {
                                    // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
                                    showLargeImageDialog(
                                        getage.poseImage,
                                        getage.title,
                                        getage.content
                                    )
                                }
                                // 버튼 동작 처리
                                var isButtonFilled = false
                                imageButton.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)

                                imageButton.setOnClickListener {
                                    isButtonFilled = !isButtonFilled
                                    if (isButtonFilled) {
                                        imageButton.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                                        onHeartButtonClick(
                                            getage.id,
                                            getage.poseImage,
                                            getage.tagnames
                                        )
                                        Log.d("HeartButtonClick1", getage.id.toString())
                                    } else {
                                        imageButton.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                                        Log.d("HeartButtonClick", "Canceled Pose ID: ${getage.id}")
                                        sharedViewModel.deleteImage(getage.id)
                                    }
                                }
                                sharedViewModel.removedImage.observe(viewLifecycleOwner) { removedImageId ->
                                    Log.d("removedImage ID2", removedImageId.toString())
                                    Log.d("removedImage ID22", getage.id.toString())
                                    if (removedImageId == getage.id) { // 이미지뷰의 ID를 직접 비교합니다.
                                        imageButton.setBackgroundResource(R.drawable._icon__heart_)
                                        isButtonFilled = false
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<RetrofitClient.PoseGetageResponse>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit27", errorMessage)
                }
            })
        return RootView
    }

    private val showLargeImageDialog: (String, String, String) -> Unit =
        { imageUrl, title, content ->
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

            // AlertDialog를 생성합니다.
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(dialogView)

            // AlertDialog를 표시합니다.
            val alertDialog = builder.create()
            alertDialog.show()

            val imageButton1 = dialogView.findViewById<ImageButton>(R.id.imageButton)
            imageButton1.setOnClickListener {
                showPopupMenu(it,largeImageView.id)
            }
        }

        private fun showPopupMenu(view: View, imageId: Int) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_second)
        popupMenu.setOnMenuItemClickListener { item ->
            // 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
            when (item.itemId) {
                R.id.item1 -> {
                    Log.d("Popup", "Adding pose with imageId: $imageId")
                    val viewModel =
                        ViewModelProvider(requireActivity()).get(MyCustomViewModel::class.java)
                    viewModel.addImageId(imageId)
                    rvAdapter.notifyDataSetChanged()

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
