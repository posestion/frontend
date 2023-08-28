package com.example.posestion

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.RvPhotoBinding

class AdapterPhoto (private val photoList: MutableList<RetrofitClient.mypagephoto>,
                    private val context: Context
): RecyclerView.Adapter<AdapterPhoto.viewHolder>() {

    inner class viewHolder(private val binding: RvPhotoBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : RetrofitClient.mypagephoto){

            val imageUrl = list.poseimage
            val imageView = binding.rvphotoImage

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            imageView.clipToOutline = true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount() = photoList.size
}