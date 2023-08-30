package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassListItemBinding

class board_myclass_adapter(private val myclass: List<RetrofitClient.listenmyclass>) : RecyclerView.Adapter<board_myclass_adapter.myclass_viewholder>(){
    class myclass_viewholder(private val binding: BoardClassListItemBinding):RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart:Int = 0
        fun bind(myclass : RetrofitClient.listenmyclass){
            heart = myclass.dibs
            val imageURL = myclass.Image_url
            binding.boardHotclassTitleTxt.text = myclass.title
            val image = binding.boardHotclassThumbnail
            val id = myclass.id

            Glide.with(context)
                .load(imageURL)
                .into(image)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myclass_viewholder {
        return myclass_viewholder(BoardClassListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return myclass.size
    }

    override fun onBindViewHolder(holder: myclass_viewholder, position: Int) {
        holder.bind(myclass[position])
    }
}