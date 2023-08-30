package com.example.posestion

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.MypageRvContentsBinding

class AdapterMypageContents(private val ContentsList: MutableList<RetrofitClient.mypageContent>,
                            private val resources: Resources,
                            private val context: Context
): RecyclerView.Adapter<AdapterMypageContents.viewHolder>() {

    inner class viewHolder(private val binding: MypageRvContentsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : RetrofitClient.mypageContent){
            binding.mypageRvContentsTitle.text = list.title
            binding.mypageRvContentsText.text = list.content
            binding.mypageRvContentsTextHeart.text = list.likenum.toString()
            binding.mypageRvContentsTextReply.text = list.replynum.toString()

            val imageUrl = list.image
            val imageView = binding.mypageRvContentsImageMain

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            imageView.clipToOutline = true

            if(list.like == 1){
                binding.mypageRvContentsImage1.setImageResource(R.drawable.svg_heart)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = MypageRvContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(ContentsList[position])
    }

    override fun getItemCount() = ContentsList.size
}