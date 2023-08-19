package com.example.posestion

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.posestion.MyApplication.Companion.user
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFragment3 : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var retrofitServiceWithToken: RetrofitAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val token = user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())
    }

    private fun onHeartButtonClick(imageId: Int) {
        sharedViewModel.addNewImage(imageId)
        Log.d(imageId.toString(),"bbb")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_layout_3, container, false)

        retrofitServiceWithToken.posehotboard().enqueue(object : Callback<RetrofitClient.PoseHotboardResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseHotboardResponse>,
                response: Response<RetrofitClient.PoseHotboardResponse>
            ) {
                if (response.isSuccessful) {
                    val hotboardResponse = response.body()
                    val hotboardDataList = hotboardResponse?.result

                    if (!hotboardDataList.isNullOrEmpty()) {
                        for (i in 0 until hotboardDataList.size) {
                            val hotboard = hotboardDataList[i]

                            val imageView: ImageView = rootView.findViewById(
                                resources.getIdentifier(
                                    "imageView${i + 1}",
                                    "id",
                                    requireContext().packageName
                                )
                            )
                            val imageButton: ImageButton = rootView.findViewById(
                                resources.getIdentifier(
                                    "heartButton${i + 1}",
                                    "id",
                                    requireContext().packageName
                                )
                            )

                            // 이미지 URL 가져와서 이미지뷰에 로드
                            val imageUrl = hotboard.poseImage
                            Glide.with(requireContext())
                                .load(imageUrl)
                                .centerCrop()
                                .into(imageView)

                            // 버튼 동작 처리
                            var isButtonFilled = false
                            imageButton.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)

                            imageButton.setOnClickListener {
                                isButtonFilled = !isButtonFilled
                                if (isButtonFilled) {
                                    imageButton.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                                    onHeartButtonClick(hotboard.id)
                                } else {
                                    imageButton.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                                    Log.d("HeartButtonClick", "Canceled Pose ID: ${hotboard.id}")
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.PoseHotboardResponse>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit22", errorMessage)
            }
        })

        return rootView
    }

    private val showLargeImageDialog: (Int) -> Unit = { imageId ->
        // 이미지를 크게 보여주는 AlertDialog를 표시하는 로직을 작성
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_large_image, null)

        // 커스텀 레이아웃의 이미지뷰를 찾습니다.
        val largeImageView = dialogView.findViewById<ImageView>(R.id.largeImageView)
        largeImageView.setImageResource(imageId) // 이미지 설정

        // AlertDialog를 생성합니다.
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        // AlertDialog를 표시합니다.
        val alertDialog = builder.create()
        alertDialog.show()

        val imageButton1 = dialogView.findViewById<ImageButton>(R.id.imageButton)
        imageButton1.setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_second)
        popupMenu.setOnMenuItemClickListener { item ->
            // 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
            when (item.itemId) {
                R.id.item1 -> {
                    // "포즈삭제" 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
                    // 여기서 원하는 동작을 구현하면 됩니다.
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