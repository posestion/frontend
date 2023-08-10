package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardBasicListItemBinding
import timber.log.Timber

class basic_mypick_adapter(private val basic_mypick: List<basic_mypick>):RecyclerView.Adapter<basic_mypick_adapter.basic_mypick_ViewHolder>() {
    class basic_mypick_ViewHolder(private val binding: BoardBasicListItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(basic_mypick: basic_mypick){
                    binding.boardHotclassTitleTxt.text = basic_mypick.title
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): basic_mypick_ViewHolder {
        Timber.d("onCreateViewHolder")
        return basic_mypick_ViewHolder(
            BoardBasicListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = basic_mypick.size

    override fun onBindViewHolder(holder: basic_mypick_ViewHolder, position: Int) {
        Timber.d("onCreateViewHolder")
        holder.bind(basic_mypick[position])
    }
}

