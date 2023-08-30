package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassViewViewpagerBinding

class board_class_view_viewpager_adapter(private var pageList: List<RetrofitClient.classdetailimage>) : RecyclerView.Adapter<board_class_view_viewpager_adapter.classview_viewholder>(){
    class classview_viewholder(private val binding: BoardClassViewViewpagerBinding):RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bind(pageitem: RetrofitClient.classdetailimage){
            val imageURL = pageitem.Image_url
            val image = binding.classViewImage
            Glide.with(context)
                .load(imageURL)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): classview_viewholder {
        return classview_viewholder(BoardClassViewViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: classview_viewholder, position: Int) {
        holder.bind(pageList[position])
    }
}