package com.example.posestion

import android.app.AlertDialog
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

class MyFragment3 : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun onHeartButtonClick(imageId: Int) {
        sharedViewModel.addNewImage(imageId)
        Log.d(imageId.toString(),"bbb")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_layout_3, container, false)

        // 새로운 이미지뷰를 찾습니다. (XML에서 적절한 ID를 지정해야 합니다.)
        val newImageView = rootView.findViewById<View>(R.id.imageView)
        val newImageViewId = R.drawable.rectangle_67
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog(newImageViewId)
        }
        val buttonEvent = rootView.findViewById<View>(com.example.posestion.R.id.heartButton1)
        var isButtonFilled = false

        buttonEvent.setOnClickListener {
            isButtonFilled = !isButtonFilled
            if (isButtonFilled) {
                buttonEvent.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                onHeartButtonClick(R.drawable.rectangle_67)
            } else {
                buttonEvent.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                sharedViewModel.removeImage(R.drawable.rectangle_67)
            }
        }

        sharedViewModel.removedImage.observe(viewLifecycleOwner) { removedImageId ->
            Log.d("removedImage ID2", removedImageId.toString())
            Log.d("removedImage ID22", newImageViewId.toString())
            if (removedImageId == newImageViewId) { // 이미지뷰의 ID를 직접 비교합니다.
                buttonEvent.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                isButtonFilled = false
            }
        }

        val newImageView2 = rootView.findViewById<View>(com.example.posestion.R.id.imageView2)
        val newImageViewId2 = R.drawable.rectangle_67
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView2.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog(newImageViewId2)
        }

        val buttonEvent2 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton2)
        var isButtonFilled2 = false

        buttonEvent2.setOnClickListener {
            isButtonFilled2 = !isButtonFilled2
            if (isButtonFilled2) {
                buttonEvent2.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                onHeartButtonClick(R.drawable.rectangle_67)
            } else {
                buttonEvent2.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                sharedViewModel.removeImage(R.drawable.rectangle_67)
            }
        }

        val newImageView3 = rootView.findViewById<View>(com.example.posestion.R.id.imageView3)
        val newImageViewId3 = R.drawable.rectangle_67
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView3.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog(newImageViewId3)
        }
        val buttonEvent3 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton3)
        var isButtonFilled3 = false

        buttonEvent3.setOnClickListener {
            isButtonFilled3 = !isButtonFilled3
            if (isButtonFilled3) {
                buttonEvent3.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                onHeartButtonClick(R.drawable.rectangle_67)
            } else {
                buttonEvent3.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                sharedViewModel.removeImage(R.drawable.rectangle_67)
            }
        }

        val newImageView4 = rootView.findViewById<View>(com.example.posestion.R.id.imageView4)
        val newImageViewId4 = R.drawable.rectangle_67
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView4.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog(newImageViewId4)
        }
        val buttonEvent4 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton4)
        var isButtonFilled4 = false

        buttonEvent4.setOnClickListener {
            isButtonFilled4 = !isButtonFilled4
            if (isButtonFilled4) {
                buttonEvent4.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                onHeartButtonClick(R.drawable.rectangle_67)
            } else {
                buttonEvent4.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                sharedViewModel.removeImage(R.drawable.rectangle_67)
            }
        }

        val newImageView5 = rootView.findViewById<View>(com.example.posestion.R.id.imageView5)
        val newImageViewId5 = R.drawable.rectangle_67
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView5.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog(newImageViewId5)
        }
        val buttonEvent5 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton5)
        var isButtonFilled5 = false

        buttonEvent5.setOnClickListener {
            isButtonFilled5 = !isButtonFilled5
            if (isButtonFilled5) {
                buttonEvent5.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
                onHeartButtonClick(R.drawable.rectangle_67)
            } else {
                buttonEvent5.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
                sharedViewModel.removeImage(R.drawable.rectangle_67)
            }
        }

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