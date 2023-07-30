package com.example.posestion

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment

class MyFragment3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_layout_3, container, false)

        // 새로운 이미지뷰를 찾습니다. (XML에서 적절한 ID를 지정해야 합니다.)
        val newImageView = rootView.findViewById<View>(R.id.imageView)
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog()
        }
        val buttonEvent = rootView.findViewById<View>(com.example.posestion.R.id.heartButton1)
        var isButtonFilled = false

        buttonEvent.setOnClickListener {
            isButtonFilled = !isButtonFilled
            if (isButtonFilled) {
                buttonEvent.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
            } else {
                buttonEvent.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
            }
        }


        val newImageView2 = rootView.findViewById<View>(com.example.posestion.R.id.imageView2)
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView2.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog()
        }

        val buttonEvent2 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton2)
        var isButtonFilled2 = false

        buttonEvent2.setOnClickListener {
            isButtonFilled2 = !isButtonFilled2
            if (isButtonFilled2) {
                buttonEvent2.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
            } else {
                buttonEvent2.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
            }
        }

        val newImageView3 = rootView.findViewById<View>(com.example.posestion.R.id.imageView3)
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView3.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog()
        }
        val buttonEvent3 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton3)
        var isButtonFilled3 = false

        buttonEvent3.setOnClickListener {
            isButtonFilled3 = !isButtonFilled3
            if (isButtonFilled3) {
                buttonEvent3.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
            } else {
                buttonEvent3.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
            }
        }

        val newImageView4 = rootView.findViewById<View>(com.example.posestion.R.id.imageView4)
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView4.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog()
        }
        val buttonEvent4 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton4)
        var isButtonFilled4 = false

        buttonEvent4.setOnClickListener {
            isButtonFilled4 = !isButtonFilled4
            if (isButtonFilled4) {
                buttonEvent4.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
            } else {
                buttonEvent4.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
            }
        }

        val newImageView5 = rootView.findViewById<View>(com.example.posestion.R.id.imageView5)
        // 새로운 이미지뷰에 클릭 리스너를 등록합니다.
        newImageView5.setOnClickListener {
            // 이미지를 크게 보여주는 AlertDialog를 표시합니다.
            showLargeImageDialog()
        }
        val buttonEvent5 = rootView.findViewById<View>(com.example.posestion.R.id.heartButton5)
        var isButtonFilled5 = false

        buttonEvent5.setOnClickListener {
            isButtonFilled5 = !isButtonFilled5
            if (isButtonFilled5) {
                buttonEvent5.setBackgroundResource(com.example.posestion.R.drawable.fillheart)
            } else {
                buttonEvent5.setBackgroundResource(com.example.posestion.R.drawable._icon__heart_)
            }
        }

        return rootView

    }


    private fun showLargeImageDialog() {
        // 커스텀 레이아웃을 인플레이트합니다.
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_large_image, null)

        // 커스텀 레이아웃의 이미지뷰를 찾습니다.
        val largeImageView = dialogView.findViewById<ImageView>(R.id.largeImageView)

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