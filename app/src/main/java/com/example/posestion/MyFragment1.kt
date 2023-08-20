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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MyFragment1 : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter

    // 이미지를 추가하는 로직
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        imageAdapter = ImageAdapter(sharedViewModel = sharedViewModel, showLargeImageDialog = showLargeImageDialog)
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

    private val showLargeImageDialog: (Int) -> Unit = { imageId ->
        // 이미지를 크게 보여주는 AlertDialog를 표시하는 로직을 작성
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_large_image, null)

        // 커스텀 레이아웃의 이미지뷰를 찾습니다.
        val largeImageView = dialogView.findViewById<ImageView>(R.id.largeImageView)
        val imageUrl = sharedViewModel.getImageUrlForId(imageId) // 이미지 아이디로 이미지 URL 가져오기
        if (imageUrl != null) {
            Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .into(largeImageView)
        }

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
//com.example.posestion.