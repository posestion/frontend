package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardClassListItemBinding

class board_class_home_hotclass_adapter(private val hotclass: List<hotclass>):RecyclerView.Adapter<board_class_home_hotclass_adapter.class_home_hotclass_viewholder>() {
    class class_home_hotclass_viewholder(private val binding: BoardClassListItemBinding):RecyclerView.ViewHolder(binding.root){
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): class_home_hotclass_viewholder {
        return class_home_hotclass_viewholder(BoardClassListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: class_home_hotclass_viewholder, position: Int) {
        holder.bind(hotclass[position])
    }
}