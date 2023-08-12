package com.example.posestion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.posestion.databinding.ItemRecyclerviewBinding

class MyRecyclerViewAdapter(private val viewModel: MyCustomViewModel):  RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>(){

    private val addedImageIds = mutableListOf<Int>()

    private val imageIdsObserver = Observer<List<Int>> { newImageList ->
        addedImageIds.clear()
        addedImageIds.addAll(newImageList)
        notifyDataSetChanged()
        Log.d("MyRecyclerViewAdapter1", "updateData() called with newImageList size: ${newImageList.size}")
    }

    init {
        viewModel.addedImageIds.observeForever(imageIdsObserver)
    }
    fun updateData(newImageList: List<Int>) {
        addedImageIds.clear() // 기존 데이터 모두 제거
        addedImageIds.addAll(newImageList) // 새로운 데이터 추가
        notifyDataSetChanged()
        Log.d("MyRecyclerViewAdapter2", "updateData() called with newImageList size: ${newImageList.size}")
    }
    inner class ViewHolder(private val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageId: Int, position: Int) {
            Log.d("MyRecyclerViewAdapter6", "bind() called for position: $position")
            binding.tvMain.text = "${position + 1}"

            binding.imageButton1.setOnClickListener {
                popupMenuClickListener?.onPopupMenuClick(it, position)
            }

            binding.imageView.setImageResource(imageId)

        }
    }
    fun addPose(poseImageResource: Int) {
        addedImageIds.add(poseImageResource)
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        if (position in 0 until addedImageIds.size) {
            addedImageIds.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, addedImageIds.size) // 변경된 위치 이후의 아이템들을 다시 그리도록 알려줌
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("MyRecyclerViewAdapter8", "onBindViewHolder called for position: $position")
        holder.bind(addedImageIds[position], position)
    }

    override fun getItemCount(): Int {
        Log.d("MyRecyclerViewAdapter9", addedImageIds.size.toString())
        return addedImageIds.size
    }

    // 인터페이스를 정의하여 클릭 이벤트를 처리할 수 있도록 합니다.
    interface OnPopupMenuClickListener {
        fun onPopupMenuClick(view: View, position: Int)
    }

    private var popupMenuClickListener: OnPopupMenuClickListener? = null

    fun setOnPopupMenuClickListener(listener: OnPopupMenuClickListener) {
        this.popupMenuClickListener = listener
    }
    fun onDestroy() {
        viewModel.addedImageIds.removeObserver(imageIdsObserver)
    }
}