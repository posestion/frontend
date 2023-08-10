package com.example.posestion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.posestion.databinding.ItemRecyclerviewBinding

class MyRecyclerViewAdapter():  RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>(){
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val addedImageIds: MutableSet<Int> = mutableSetOf()

    fun removeData(position: Int) {
        if (position in 0 until dataSet.size) {
            dataSet.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataSet.size) // 변경된 위치 이후의 아이템들을 다시 그리도록 알려줌
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("MyRecyclerViewAdapter3", addedImageIds.toString())
        val imageId: Int? = addedImageIds.elementAtOrNull(position)

        if (imageId != null) {
            holder.bind(imageId, position)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Create a GridAdapter instance and set it to the ImageView
        private val gridAdapter = GridAdapter(binding.imageView)

        init {
            // 이미지 리소스 설정 (이미지 설정은 ViewHolder가 생성될 때 한 번만 이루어집니다.)
            binding.imageView.setImageResource(R.drawable.rectangle_67)
        }

        fun bind(imageId: Int, position: Int) {
            binding.tvMain.text = "${position + 1}"

            binding.imageButton1.setOnClickListener {
                popupMenuClickListener?.onPopupMenuClick(it, position)
            }
            binding.imageView.setImageResource(imageId)
        }
    }
    fun addPose(poseImageResource: Int) {
        if (addedImageIds.contains(poseImageResource)) {
            return // 이미 추가된 이미지 ID일 경우 중복 추가 방지
        }

        // 이미지 아이디를 추가하고 중복 확인
        addedImageIds.add(poseImageResource)
        Log.d("MyRecyclerViewAdapter2",  addedImageIds.toString())
        // 데이터 중복 확인 후 추가
        val newData = listOf("${dataSet.size + 1}", "image_${poseImageResource % 8}")
        if (!dataSet.contains(newData)) {
            dataSet.add(newData)
            Log.d("MyRecyclerViewAdapter", dataSet.toString())
            notifyItemInserted(dataSet.size - 1)
        }
    }

    fun getItemData(position: Int): List<String> {
        return dataSet[position]
    }

    // 인터페이스를 정의하여 클릭 이벤트를 처리할 수 있도록 합니다.
    interface OnPopupMenuClickListener {
        fun onPopupMenuClick(view: View, position: Int)
    }

    private var popupMenuClickListener: OnPopupMenuClickListener? = null

    fun setOnPopupMenuClickListener(listener: OnPopupMenuClickListener) {
        this.popupMenuClickListener = listener
    }
}