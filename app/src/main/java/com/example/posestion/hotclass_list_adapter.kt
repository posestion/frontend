package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardHotclassListItemBinding
import timber.log.Timber

class hotclass_list_adapter(private val hotclass: List<hotclass>) : RecyclerView.Adapter<hotclass_list_adapter.hotclass_ViewHolder>(){

    class hotclass_ViewHolder(private val binding: BoardHotclassListItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(hotclass : hotclass){
                    binding.boardHotclassTitleTxt.text = hotclass.title
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): hotclass_ViewHolder {
        Timber.d("onCreateViewHolder")
        return hotclass_ViewHolder(
            BoardHotclassListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = hotclass.size

    override fun onBindViewHolder(holder: hotclass_ViewHolder, position: Int) {
        Timber.d("onCreateViewHolder")
        holder.bind(hotclass[position])
    }
}