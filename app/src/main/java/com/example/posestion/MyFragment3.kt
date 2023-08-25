package com.example.posestion

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.posestion.MyApplication.Companion.user
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MyFragment3 : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var retrofitServiceWithToken: RetrofitAPI
    private var filterdates: ArrayList<RetrofitClient.PoseFilterdate>? = null
    inline fun <reified T : Parcelable> Intent.getParcelableArrayListExtraCompat(key: String): ArrayList<T>? =
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val token = user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())


        Log.d("RetrofitSearch42", filterdates.toString())

        var test= getArguments()?.getString("test1")
        Log.d("RetrofitSearch44", test.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("RetrofitSearch41", filterdates.toString())
        filterdates?.let { nonNullFilterdates ->
            for ((index, poseSearch) in nonNullFilterdates.withIndex()) {
                val imageView: ImageView = view.findViewById(
                    resources.getIdentifier(
                        "imageView${index + 1}",
                        "id",
                        requireContext().packageName
                    )
                )
                val imageButton: ImageButton = view.findViewById(
                    resources.getIdentifier(
                        "heartButton${index + 1}",
                        "id",
                        requireContext().packageName
                    )
                )
                val textView: TextView? = view.findViewById(
                    resources.getIdentifier(
                        "tagText${index + 1}",
                        "id",
                        requireContext().packageName
                    )
                )

                if (textView != null) {
                    val tagNames = poseSearch.tagname
                    Log.d("RetrofitSearch3", tagNames.toString())
                    val tagText = if (!tagNames.isNullOrEmpty()) {
                        "#$tagNames"
                    } else {
                        ""
                    }
                    textView.text = tagText
                }
                // 이미지 URL 가져와서 이미지뷰에 로드
                val imageUrl = poseSearch.poseImage
                Glide.with(requireContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageView)

                // 버튼 동작 처리
                var isButtonFilled = false
                imageButton.setBackgroundResource(R.drawable._icon__heart_)

                imageButton.setOnClickListener {
                    isButtonFilled = !isButtonFilled
                    if (isButtonFilled) {
                        imageButton.setBackgroundResource(R.drawable.fillheart)
                        Log.d("HeartButtonClick1", poseSearch.id.toString())
                    } else {
                        imageButton.setBackgroundResource(R.drawable._icon__heart_)
                        Log.d("HeartButtonClick", "Canceled Pose ID: ${poseSearch.id}")
                        sharedViewModel.deleteImage(poseSearch.id)
                    }
                }
            }
        }

        val searchResults = arguments?.getSerializable("searchResults") as? ArrayList<RetrofitClient.PoseSearch>
        Log.d("RetrofitSearch4", searchResults.toString())
        if (searchResults != null) {
            for ((index, poseSearch) in searchResults.withIndex()) {
                val imageView: ImageView = view.findViewById(
                    resources.getIdentifier(
                        "imageView${index + 1}",
                        "id",
                        requireContext().packageName
                    )
                )
                val imageButton: ImageButton = view.findViewById(
                    resources.getIdentifier(
                        "heartButton${index + 1}",
                        "id",
                        requireContext().packageName
                    )
                )
                val textView: TextView? = view.findViewById(
                    resources.getIdentifier(
                        "tagText${index + 1}",
                        "id",
                        requireContext().packageName
                    )
                )

                if (textView != null) {
                    val tagNames = poseSearch.tagname
                    Log.d("RetrofitSearch3", tagNames.toString())
                    val tagText = if (tagNames != null) {
                        tagNames.filterNotNull().joinToString(", ") { tag -> "#$tag" }
                    } else {
                        ""
                    }
                    textView.text = tagText
                }
                // 이미지 URL 가져와서 이미지뷰에 로드
                val imageUrl = poseSearch.poseImage
                Glide.with(requireContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageView)

                // 버튼 동작 처리
                var isButtonFilled = false
                imageButton.setBackgroundResource(R.drawable._icon__heart_)

                imageButton.setOnClickListener {
                    isButtonFilled = !isButtonFilled
                    if (isButtonFilled) {
                        imageButton.setBackgroundResource(R.drawable.fillheart)
                        onHeartButtonClick(poseSearch.id, poseSearch.poseImage, poseSearch.tagname)
                        Log.d("HeartButtonClick1", poseSearch.id.toString())
                    } else {
                        imageButton.setBackgroundResource(R.drawable._icon__heart_)
                        Log.d("HeartButtonClick", "Canceled Pose ID: ${poseSearch.id}")
                        sharedViewModel.deleteImage(poseSearch.id)
                    }
                }
            }
        }
    }

    private fun onHeartButtonClick(imageId: Int,imageUrl: String, tagNames: List<String>?) {
        Log.d(imageId.toString(),"bbb")

        retrofitServiceWithToken.poseaddfavorite(imageId).enqueue(object : Callback<RetrofitClient.PoseAddfavoriteResponse> {
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

                            requireActivity().runOnUiThread {
                                val textView: TextView? = rootView.findViewById(
                                    resources.getIdentifier(
                                        "tagText${i + 1}",
                                        "id",
                                        requireContext().packageName
                                    )
                                )

                                if (textView != null) {
                                    val tagNames = hotboard.tagname
                                    val tagText = if (tagNames != null) {
                                        tagNames.filterNotNull().joinToString(", ") { tag -> "#$tag" }
                                    } else {
                                        ""
                                    }
                                    textView.text = tagText
                                }
                            }

                            // 이미지 URL 가져와서 이미지뷰에 로드
                            val imageUrl = hotboard.poseImage
                            Glide.with(requireContext())
                                .load(imageUrl)
                                .centerCrop()
                                .into(imageView)

                            imageView.setOnClickListener {
                                // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
                                showLargeImageDialog(hotboard.poseImage, hotboard.title, hotboard.content)
                            }
                            // 버튼 동작 처리
                            var isButtonFilled = false
                            imageButton.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)

                            imageButton.setOnClickListener {
                                isButtonFilled = !isButtonFilled
                                if (isButtonFilled) {
                                    imageButton.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                                    onHeartButtonClick(hotboard.id,hotboard.poseImage, hotboard.tagname)
                                    Log.d("HeartButtonClick1", hotboard.id.toString())
                                } else {
                                    imageButton.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                                    Log.d("HeartButtonClick", "Canceled Pose ID: ${hotboard.id}")
                                    sharedViewModel.deleteImage(hotboard.id)
                                }
                            }
                            sharedViewModel.removedImage.observe(viewLifecycleOwner) { removedImageId ->
                                Log.d("removedImage ID2", removedImageId.toString())
                                Log.d("removedImage ID22", hotboard.id.toString())
                                if (removedImageId == hotboard.id) { // 이미지뷰의 ID를 직접 비교합니다.
                                    imageButton.setBackgroundResource(R.drawable._icon__heart_)
                                    isButtonFilled = false
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

    private val showLargeImageDialog: (String, String, String) -> Unit = { imageUrl, title, content ->
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

    fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): T
    {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.intent.getSerializableExtra(name, clazz)!!
        else
            activity.intent.getSerializableExtra(name) as T
    }
}