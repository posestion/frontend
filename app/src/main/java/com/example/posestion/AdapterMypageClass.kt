package com.example.posestion

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.MypageRvClassBinding

class AdapterMypageClass (private val ClassList: MutableList<com.example.posestion.DataClass>,
                          private val resources: Resources
): RecyclerView.Adapter<AdapterMypageClass.viewHolder>() {

    inner class viewHolder(private val binding: MypageRvClassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : com.example.posestion.DataClass){
            binding.mypageRvClassTitle.text = list.title

            val dp86 = (86 * Resources.getSystem().displayMetrics.density).toInt()

            val targetSize = dp86
            val drawableRes = list.image
            val drawable = ResourcesCompat.getDrawable(resources, drawableRes, null)

            if (drawable != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, targetSize, targetSize, true))

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
        holder.bind(ClassList[position])
    }

    override fun getItemCount() = ClassList.size
}