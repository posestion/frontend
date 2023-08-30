package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.MyApplication.Companion.user
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassListItemBinding
import timber.log.Timber

class hotclass_list_adapter(private val hotclass: List<RetrofitClient.hotclass>) : RecyclerView.Adapter<hotclass_list_adapter.hotclass_ViewHolder>(){

    class hotclass_ViewHolder(val binding: BoardClassListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart:Int = 0

                fun bind(hotclass : RetrofitClient.hotclass){
                    heart = hotclass.dibs
                    val imageURL = hotclass.Image_url
                    binding.boardHotclassTitleTxt.text = hotclass.title
                    val image = binding.boardHotclassThumbnail

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
                        intent.putExtra("title",hotclass.title)
                        intent.putExtra("thumbnail",hotclass.Image_url)
                        intent.putExtra("heart",heart)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): hotclass_ViewHolder {
        Timber.d("onCreateViewHolder")
        return hotclass_ViewHolder(
            BoardClassListItemBinding.inflate(
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