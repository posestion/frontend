package com.example.posestion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.posestion.databinding.ItemRecyclerviewBinding

class MyRecyclerViewAdapter:  RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>(){
    private val dataSet: ArrayList<List<String>> = arrayListOf<List<String>>().apply {
        for (i in 1..5) {
            add(listOf("$i", "image_${i % 8}")) // 이미지 리소스 이름 추가
        }
    }

    fun removeData(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
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
            binding.imageView.setImageResource(R.drawable.baseline_square_24)
        }

        fun bind(data: List<String>, position: Int) {
            binding.tvMain.text = data[0]

            binding.imageButton1.setOnClickListener {
                // PopupMenu를 생성하고 메뉴를 표시합니다.
                // 이제 인터페이스를 통해 showPopupMenu 함수를 호출합니다.
                popupMenuClickListener?.onPopupMenuClick(it, position)
            }

            // 이미지 리소스 설정
            gridAdapter.setImage(R.drawable.baseline_square_24)
        }
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