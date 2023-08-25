package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardClassHomeMasterlistItemBinding

class board_class_home_masterlist_adapter (private val home_masterlist: List<home_masterlist>):RecyclerView.Adapter<board_class_home_masterlist_adapter.class_home_masterlist_viewholder>(){
    class class_home_masterlist_viewholder(private val binding: BoardClassHomeMasterlistItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(home_masterlist : home_masterlist){
            binding.boardClassHomeMasterlistNum.text = home_masterlist.num.toString()
            binding.boardClassHomeMasterlistName.text = home_masterlist.name
            binding.boardClassHomeMasterlistEx.text = home_masterlist.ex
            binding.boardClassHomeMasterlistJob.text = home_masterlist.job
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): class_home_masterlist_viewholder {
        return class_home_masterlist_viewholder(BoardClassHomeMasterlistItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: class_home_masterlist_viewholder, position: Int) {
        holder.bind(home_masterlist[position])
    }
}