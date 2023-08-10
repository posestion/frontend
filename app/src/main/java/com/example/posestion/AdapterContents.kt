package com.example.posestion

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.RvContentsBinding

class AdapterContents(private val ContentsList: MutableList<com.example.posestion.DataContents>,
                      private val context: Context,
                      private val resources: Resources): RecyclerView.Adapter<AdapterContents.viewHolder>() {

    inner class viewHolder(private val binding: RvContentsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : com.example.posestion.DataContents){
            binding.RvcontentsTitle.text = list.title
            binding.RvcontentsText.text = list.text
            binding.RvcontentsTextHeart.text = list.heart
            binding.RvcontentsTextComment.text = list.comment

            val pixels = (120 * Resources.getSystem().displayMetrics.density).toInt()
            val dp18 = (18 * Resources.getSystem().displayMetrics.density).toInt()
            val dp100 = (100 * Resources.getSystem().displayMetrics.density).toInt()
            val dp55 = (55 * Resources.getSystem().displayMetrics.density).toInt()

            val targetSize = dp55
            val drawableRes = list.image
            val drawable = ResourcesCompat.getDrawable(resources, drawableRes, null)

            if (drawable != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, targetSize, targetSize, true))

                binding.RvcontentsImageMain.setImageDrawable(scaledDrawable)
                binding.RvcontentsImageMain.clipToOutline = true
            }

            binding.RvcontentsBtnMenu.setOnClickListener {
                val popupMenuView = LayoutInflater.from(context).inflate(R.layout.custom_popup, null)
                val popupWindow = PopupWindow(popupMenuView, pixels, ViewGroup.LayoutParams.WRAP_CONTENT)

                val deleteButton = popupMenuView.findViewById<Button>(R.id.popup_delete)
                val editButton = popupMenuView.findViewById<Button>(R.id.popup_edit)
                val keepButton = popupMenuView.findViewById<Button>(R.id.popup_keep)

                deleteButton.setOnClickListener {
                    Toast.makeText(context, "Delete 버튼 클릭", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }

                editButton.setOnClickListener {
                    Toast.makeText(context, "Edit 버튼 클릭", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }

                keepButton.setOnClickListener {
                    Toast.makeText(context, "Keep 버튼 클릭", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }

                popupWindow.isOutsideTouchable = true

                val location = IntArray(2)
                binding.RvcontentsBtnMenu.getLocationOnScreen(location)

                val x = location[0]-dp100 // x 좌표
                val y = location[1]+dp18 // y 좌표

                popupWindow.showAtLocation(binding.RvcontentsBtnMenu, Gravity.NO_GRAVITY, x, y)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(ContentsList[position])
    }

    override fun getItemCount() = ContentsList.size
}