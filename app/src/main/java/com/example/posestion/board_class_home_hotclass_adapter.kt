package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassListItemBinding

class board_class_home_hotclass_adapter(private val gethotclass: List<RetrofitClient.getHotClass>):RecyclerView.Adapter<board_class_home_hotclass_adapter.class_home_hotclass_viewholder>() {
    class class_home_hotclass_viewholder(private val binding: BoardClassListItemBinding):RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart:Int = 0
        fun bind(gethotclass : RetrofitClient.getHotClass){
            heart = gethotclass.dibs
            val imageURL = gethotclass.Image_url
            binding.boardHotclassTitleTxt.text = " "
            val image = binding.boardHotclassThumbnail

            Glide.with(context)
                .load(imageURL)
                .into(image)
            val id = gethotclass.id

            if(heart==0){
                binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_empty_heart)
            }
            else if(heart==1){
                binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_full_heart)
            }
            itemView.setOnClickListener {
                val intent = Intent(context,board_class_view::class.java)
                intent.putExtra("id",id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): class_home_hotclass_viewholder {
        return class_home_hotclass_viewholder(BoardClassListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = gethotclass.size

    override fun onBindViewHolder(holder: class_home_hotclass_viewholder, position: Int) {
        holder.bind(gethotclass[position])
    }
}