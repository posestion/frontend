package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassListItemBinding
import timber.log.Timber

class home_hotclass_list_adapter (private val getclass: List<RetrofitClient.getclass>) : RecyclerView.Adapter<home_hotclass_list_adapter.home_hotclass_ViewHolder>(){

    class home_hotclass_ViewHolder(private val binding: BoardClassListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(getclass : RetrofitClient.getclass){
            val imageURL = getclass.Image_url
            binding.boardHotclassTitleTxt.text = getclass.title
            val image = binding.boardHotclassThumbnail

            Glide.with(context)
                .load(imageURL)
                .into(image)


            itemView.setOnClickListener {
                val intent = Intent(context,board_class_view::class.java)
                intent.putExtra("title",getclass.title)
                intent.putExtra("thumbnail",getclass.Image_url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): home_hotclass_ViewHolder {
        Timber.d("onCreateViewHolder")
        return home_hotclass_ViewHolder(
            BoardClassListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: home_hotclass_list_adapter.home_hotclass_ViewHolder,
        position: Int
    ) {
        Timber.d("onCreateViewHolder")
        holder.bind(getclass[position])
    }

    override fun getItemCount(): Int = getclass.size}
