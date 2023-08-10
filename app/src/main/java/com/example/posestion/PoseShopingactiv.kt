package com.example.posestion

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.PoseshopshoppingBinding


class PoseShopingactiv: AppCompatActivity() {
    private lateinit var binding: PoseshopshoppingBinding
    private val rvAdapter = MyRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PoseshopshoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            with(recyclerView) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = rvAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }

        binding.bbutton.setOnClickListener {
            val intent = Intent(this, PoseShopMain::class.java)
            startActivity(intent)
        }

        // MyRecyclerViewAdapter에 showPopupMenu 함수를 호출할 수 있도록 인터페이스를 추가합니다.
        rvAdapter.setOnPopupMenuClickListener(object : MyRecyclerViewAdapter.OnPopupMenuClickListener {
            override fun onPopupMenuClick(view: View, position: Int) {
                showPopupMenu(view, position)
            }
        })
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_main)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    // "포즈삭제" 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
                    rvAdapter.removeData(position)
                    true
                }
                R.id.item2 -> {
                    // "순서바꾸기" 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
                    // 예를 들어, 포즈 순서를 바꾸거나, 다른 위치로 이동하는 등의 동작을 수행할 수 있습니다.
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}







