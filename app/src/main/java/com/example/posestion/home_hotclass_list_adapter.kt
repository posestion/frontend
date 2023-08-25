package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardClassListItemBinding
import timber.log.Timber

class home_hotclass_list_adapter (private val hotclass: List<hotclass>) : RecyclerView.Adapter<home_hotclass_list_adapter.home_hotclass_ViewHolder>(){

    class home_hotclass_ViewHolder(private val binding: BoardClassListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(hotclass : hotclass){
            binding.boardHotclassHeart.isClickable = hotclass.heart
            binding.boardHotclassTitleTxt.text = hotclass.title
            binding.boardHotclassThumbnail.setImageResource(hotclass.image)

            itemView.setOnClickListener {
                val intent = Intent(context,board_class_view::class.java)
                intent.putExtra("title",hotclass.title)
                intent.putExtra("thumbnail",hotclass.image)
                intent.putExtra("heart",hotclass.heart)
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
        holder.bind(hotclass[position])
    }

    override fun getItemCount(): Int = 4}
