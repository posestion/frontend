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
import com.example.posestion.databinding.RvHomeHotclassBinding

class AdapterHomehotclass (private val hotclasslist: MutableList<RetrofitClient.homehotclass>,
                           private val context: Context
): RecyclerView.Adapter<AdapterHomehotclass.viewHolder>() {

    inner class viewHolder(private val binding: RvHomeHotclassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list: RetrofitClient.homehotclass){

            val imageUrl = list.image
            val imageView = binding.rvhomehotclassImage

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            imageView.clipToOutline = true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvHomeHotclassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(hotclasslist[position])
    }

    override fun getItemCount() = hotclasslist.size
}