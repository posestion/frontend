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
import com.example.posestion.databinding.MypageRvClassBinding

class AdapterMypageClass (private val ClassList: MutableList<RetrofitClient.mypageclass>,
                          private val context: Context): RecyclerView.Adapter<AdapterMypageClass.viewHolder>() {
    inner class viewHolder(private val binding: MypageRvClassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list: RetrofitClient.mypageclass){

            val imageUrl = list.image
            val imageView = binding.mypageRvClassImage
            binding.mypageRvClassTitle.text = list.title

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            imageView.clipToOutline = true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = MypageRvClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(ClassList[position])
    }

    override fun getItemCount() = ClassList.size
}