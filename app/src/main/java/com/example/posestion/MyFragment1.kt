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


class MyFragment1 : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MyCustomViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var retrofitServiceWithToken: RetrofitAPI
    // 이미지를 추가하는 로직
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        imageAdapter = ImageAdapter(sharedViewModel = sharedViewModel, showLargeImageDialog = showLargeImageDialog)
        viewModel = ViewModelProvider(requireActivity()).get(MyCustomViewModel::class.java)

        val token = MyApplication.user.getString("jwt", "").toString()
        Log.d("TokenDebug", "Token: $token")
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
        Log.d("TokenDebug2", retrofitServiceWithToken.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_layout_1, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        recyclerView.adapter = imageAdapter

        sharedViewModel.imageList.observe(viewLifecycleOwner, { imageList ->
            imageAdapter.updateData(imageList)
            for (imageId in imageList) {
                Log.d("Image ID:", imageId.toString())
            }
        })
        imageAdapter = ImageAdapter(sharedViewModel = sharedViewModel, showLargeImageDialog = showLargeImageDialog)
        recyclerView.adapter = imageAdapter
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
//com.example.posestion.