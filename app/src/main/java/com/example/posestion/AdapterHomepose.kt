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
import com.example.posestion.databinding.MypageRvPoseBinding

class AdapterHomepose (private val poseList: MutableList<RetrofitClient.homepose>,
                       private val context: Context
): RecyclerView.Adapter<AdapterHomepose.viewHolder>() {

    inner class viewHolder(private val binding: MypageRvPoseBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : RetrofitClient.homepose){

            val imageUrl = list.image
            val imageView = binding.mypageRvPoseImage

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            imageView.clipToOutline = true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = MypageRvPoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(poseList[position])
    }

    override fun getItemCount() = poseList.size
}