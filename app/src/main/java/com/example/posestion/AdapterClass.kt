package com.example.posestion

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.RvClassBinding

class AdapterClass(private val ClassList: MutableList<com.example.posestion.DataClass>,
                   private val resources: Resources
): RecyclerView.Adapter<AdapterClass.viewHolder>() {

    inner class viewHolder(private val binding: RvClassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : com.example.posestion.DataClass){
            binding.RvclassTitle.text = list.title
            val displayMetrics = resources.displayMetrics
            val dpWidth = (displayMetrics.widthPixels / displayMetrics.density).toInt()
            val half = (((dpWidth/2)-30) * Resources.getSystem().displayMetrics.density).toInt()

            val Card = binding.RvclassCard
            val layoutParams = Card.layoutParams
            layoutParams.width = half
            layoutParams.height = half
            Card.layoutParams = layoutParams

            val targetSize = half
            val drawableRes = list.image
            val drawable = ResourcesCompat.getDrawable(resources, drawableRes, null)

            if (drawable != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, targetSize, targetSize, true))

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
        holder.bind(ClassList[position])
    }

    override fun getItemCount() = ClassList.size
}