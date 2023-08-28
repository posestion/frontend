package com.example.posestion

import android.app.Activity
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

    object SharedGlobals {
        var poseshopDelete: MutableList<Int>? = null
        var poseshopDeleteList: MutableList<Int>? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PoseshopshoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MyCustomViewModel::class.java)
        rvAdapter = MyRecyclerViewAdapter(viewModel)

        val receivedValue = intent.getStringExtra("key_name")
        val addedImageIds = intent.getIntegerArrayListExtra("addedImageIds") ?: ArrayList()
        val addedImageUrls = intent.getStringArrayListExtra("addedImageUrls")
        Log.d("ReceivedValue", receivedValue ?: "No value received")

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = rvAdapter

        val removedImageIds = ArrayList(viewModel.getRemovedImageIds())
        intent.putIntegerArrayListExtra("removedImageIds", removedImageIds)

        if (addedImageUrls != null) {
            addedImageIds.forEachIndexed { index, imageId ->
                viewModel.addImageId(imageId)
                val imageUrl = addedImageUrls.getOrNull(index)
                if (imageUrl != null) {
                    viewModel.addImageUrl(imageId, imageUrl)
                }
            }
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
                binding.textView6.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }
        }

        binding.checkBox.setOnClickListener {
            val allItem=viewModel.getAllImageIds()
            val deletedImageIds = mutableListOf<Int>()
            if (isButtonFilled) {
                for (i in allItem.indices.reversed()) {
                    val imageId = allItem[i]
                    rvAdapter.removeData(i)
                    deletedImageIds.add(imageId)
                }

                // 삭제된 이미지 ID 리스트를 공유 변수에 저장
                SharedGlobals.poseshopDeleteList = deletedImageIds
            }
        }

        binding.bbutton.setOnClickListener {
            val resultIntent = Intent()
            if(SharedGlobals.poseshopDelete!=null&& SharedGlobals.poseshopDeleteList==null)
            {
                SharedGlobals.poseshopDelete?.let { deletedImageIds ->
                    val deletedImageIdsArrayList = ArrayList<Int>(deletedImageIds)
                    resultIntent.putIntegerArrayListExtra("deletedPoseId", deletedImageIdsArrayList)
                }
            }
            else if(SharedGlobals.poseshopDelete==null&& SharedGlobals.poseshopDeleteList!=null)
            {
                SharedGlobals.poseshopDeleteList?.let { deletedImageIds ->
                    val deletedImageIdsArrayList = ArrayList<Int>(deletedImageIds)
                    resultIntent.putIntegerArrayListExtra("deletedPoseId", deletedImageIdsArrayList)
                }
            }
            else if(SharedGlobals.poseshopDelete!=null&& SharedGlobals.poseshopDeleteList!=null)
            {
                val combinedList = ArrayList<Int>()
                val poseshopDelete = SharedGlobals.poseshopDelete // 변수로 저장

                // SharedGlobals.poseshopDelete의 요소들 추가
                if (poseshopDelete != null) {
                    combinedList.addAll(poseshopDelete)
                }

                // SharedGlobals.poseshopDeleteList의 요소들 추가
                val poseshopDeleteList = SharedGlobals.poseshopDeleteList // 변수로 저장
                if (poseshopDeleteList != null) {
                    combinedList.addAll(poseshopDeleteList)
                }

                resultIntent.putIntegerArrayListExtra("deletedPoseId", combinedList)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            SharedGlobals.poseshopDelete=null
            finish()
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
                        rvAdapter.removeData(position)
                        if (SharedGlobals.poseshopDelete == null) {
                            SharedGlobals.poseshopDelete = mutableListOf()
                        }
                        SharedGlobals.poseshopDelete?.add(imageId)

                        Log.d("RetrofitDelFavorite6", position.toString())
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

    override fun onBackPressed() {
        val resultIntent = Intent()
        SharedGlobals.poseshopDelete?.let { deletedImageIds ->
            val deletedImageIdsArrayList = ArrayList<Int>(deletedImageIds)
            resultIntent.putIntegerArrayListExtra("deletedPoseId", deletedImageIdsArrayList)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        SharedGlobals.poseshopDelete = null
        super.onBackPressed()
    }
}







