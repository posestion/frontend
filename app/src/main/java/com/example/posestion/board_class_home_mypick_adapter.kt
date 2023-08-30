package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassListItemBinding

class board_class_home_mypick_adapter(private val getdibsclass: List<RetrofitClient.getDibsClass>):RecyclerView.Adapter<board_class_home_mypick_adapter.class_home_mypick_viewholder>(){
    class class_home_mypick_viewholder(private val binding: BoardClassListItemBinding):RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart:Boolean = false
        fun bind(getdibsclass : RetrofitClient.getDibsClass){
            heart = getdibsclass.dibs
            val imageURL = getdibsclass.Image_url
            binding.boardHotclassTitleTxt.text = getdibsclass.title
            val image = binding.boardHotclassThumbnail
            val id = getdibsclass.id

            Glide.with(context)
                .load(imageURL)
                .into(image)

            if(heart==false){
                binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_empty_heart)
            }
            else if(heart==true){
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
    ): class_home_mypick_viewholder {
        return class_home_mypick_viewholder(BoardClassListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = getdibsclass.size

    override fun onBindViewHolder(holder: class_home_mypick_viewholder, position: Int) {
        holder.bind(getdibsclass[position])
    }

}