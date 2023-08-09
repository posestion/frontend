package com.example.posestion

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.MypageRvContentsBinding

class AdapterMypageContents(private val ContentsList: MutableList<com.example.posestion.DataContents>,
                            private val resources: Resources
): RecyclerView.Adapter<AdapterMypageContents.viewHolder>() {

    inner class viewHolder(private val binding: MypageRvContentsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : com.example.posestion.DataContents){
            binding.mypageRvContentsTitle.text = list.title
            binding.mypageRvContentsText.text = list.text
            binding.mypageRvContentsTextHeart.text = list.heart
            binding.mypageRvContentsTextComment.text = list.comment

            val dp55 = (55 * Resources.getSystem().displayMetrics.density).toInt()

            val targetSize = dp55
            val drawableRes = list.image
            val drawable = ResourcesCompat.getDrawable(resources, drawableRes, null)

            if (drawable != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, targetSize, targetSize, true))

                binding.mypageRvContentsImageMain.setImageDrawable(scaledDrawable)
                binding.mypageRvContentsImageMain.clipToOutline = true
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