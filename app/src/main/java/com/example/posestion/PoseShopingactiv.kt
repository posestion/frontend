package com.example.posestion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.PoseshopshoppingBinding


class PoseShopingactiv: AppCompatActivity() {
    private lateinit var binding: PoseshopshoppingBinding
    private lateinit var viewModel: MyCustomViewModel
    private lateinit var rvAdapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PoseshopshoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MyCustomViewModel::class.java)
        rvAdapter = MyRecyclerViewAdapter(viewModel)

        val receivedValue = intent.getStringExtra("key_name")
        val addedImageIds = intent.getIntegerArrayListExtra("addedImageIds") ?: ArrayList()
        Log.d("ReceivedValue", receivedValue ?: "No value received")

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = rvAdapter

        val removedImageIds = ArrayList(viewModel.getRemovedImageIds())
        intent.putIntegerArrayListExtra("removedImageIds", removedImageIds)

        addedImageIds.forEach { imageId ->
            viewModel.addImageId(imageId)
        }
        removedImageIds.forEach { imageId ->
            viewModel.removeImageId(imageId)
        }

        viewModel.addedImageIds.observe(this, Observer { addedImageIds ->
            Log.d("ReceivedValue2", viewModel.addedImageIds.toString())
            Log.d("ReceivedValue3", addedImageIds.toString())
            rvAdapter.updateData(addedImageIds)
        })

        var isButtonFilled = false

        binding.textView6.setOnClickListener {
            isButtonFilled = !isButtonFilled
            val context = binding.root.context
            if (isButtonFilled) {
                rvAdapter.selectAllItems()
                binding.textView6.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                rvAdapter.clearSelectedItems()
                binding.textView6.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }
        }

        binding.checkBox.setOnClickListener {
            val selectedImageIds = rvAdapter.getSelectedImageIds()
            rvAdapter.removeSelectedItems()
        }

        binding.bbutton.setOnClickListener {
            val intent = Intent(this, PoseShopMain::class.java)
            intent.putExtra("key_name", "Hello from previous activity")
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
        val imageId = rvAdapter.getImageIdAtPosition(position)
        if (imageId != -1) {
            val popupMenu = PopupMenu(this, view)
            popupMenu.inflate(R.menu.menu_main)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item1 -> {
                        // "포즈삭제" 메뉴 아이템을 선택한 경우의 동작을 처리합니다.
                        val removedImageId = rvAdapter.getImageIdAtPosition(position)
                        rvAdapter.removeData(position)

                        // 이미지 아이디를 삭제하는 작업을 수행합니다.
                        viewModel.removeImageId(removedImageId)

                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        } else {
            // 이미지 아이디를 가져오는데 문제가 있을 경우에 대한 처리
        }
    }
}







