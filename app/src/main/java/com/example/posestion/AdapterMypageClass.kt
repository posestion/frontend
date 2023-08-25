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

class AdapterMypageClass (private val resources: Resources,
                          private val context: Context): RecyclerView.Adapter<AdapterMypageClass.viewHolder>() {

    private var myclass = listOf<RetrofitClient.myClass>()
    inner class viewHolder(private val binding: MypageRvClassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(pos: Int){
            val response = myclass[pos]

            val dp86 = (86 * Resources.getSystem().displayMetrics.density).toInt()

            val targetSize = dp86
            val imageUrl = response.image
            val imageView = binding.mypageRvClassImage
            binding.mypageRvClassTitle.text = response.title

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            if (imageView != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((imageView as BitmapDrawable).bitmap, targetSize, targetSize, true))

                binding.mypageRvClassImage.setImageDrawable(scaledDrawable)
                binding.mypageRvClassImage.clipToOutline = true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = MypageRvClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = myclass.size

    fun setList(list: List<RetrofitClient.myClass>) {
        myclass = list
    }
}