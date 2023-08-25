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
import com.example.posestion.databinding.RvClassBinding

class AdapterClass(private val resources: Resources,
                   private val context: Context
): RecyclerView.Adapter<AdapterClass.viewHolder>() {

    private var myclass = listOf<RetrofitClient.myClass>()

    inner class viewHolder(private val binding: RvClassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(pos: Int){
            val response = myclass[pos]

            val dp86 = (86 * Resources.getSystem().displayMetrics.density).toInt()

            val targetSize = dp86
            val imageUrl = response.image
            val imageView = binding.RvclassCard
            binding.RvclassTitle.text = response.title

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            if (imageView != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((imageView as BitmapDrawable).bitmap, targetSize, targetSize, true))

                binding.RvclassCard.setImageDrawable(scaledDrawable)
                binding.RvclassCard.clipToOutline = true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(position)
    }

    fun setList(list: List<RetrofitClient.myClass>) {
        myclass = list
    }

    override fun getItemCount() = myclass.size
}