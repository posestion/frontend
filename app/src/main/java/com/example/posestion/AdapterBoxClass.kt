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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.RvBoxClassBinding

class AdapterBoxClass (private val classlist: MutableList<RetrofitClient.mypageclass>,
                       private val resources: Resources,
                       private val context: Context
): RecyclerView.Adapter<AdapterBoxClass.viewHolder>() {

    inner class viewHolder(private val binding: RvBoxClassBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list: RetrofitClient.mypageclass){
            val dp150 = (150 * Resources.getSystem().displayMetrics.density).toInt()

            val targetSize = dp150
            val imageUrl = list.image
            val imageView = binding.rvboxclassImage
            binding.rvboxclassTitle.text = list.title

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            if (imageView != null) {
                val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap((imageView as BitmapDrawable).bitmap, targetSize, targetSize, true))

                imageView.setImageDrawable(scaledDrawable)
                imageView.clipToOutline = true
            }

            val pixels = (120 * Resources.getSystem().displayMetrics.density).toInt()
            val dp18 = (18 * Resources.getSystem().displayMetrics.density).toInt()
            val dp100 = (100 * Resources.getSystem().displayMetrics.density).toInt()

            binding.rvboxclassBtn.setOnClickListener {
                val popupMenuView = LayoutInflater.from(context).inflate(R.layout.custom_box_popup, null)
                val popupWindow = PopupWindow(popupMenuView, pixels, ViewGroup.LayoutParams.WRAP_CONTENT)

                val deleteButton = popupMenuView.findViewById<Button>(R.id.cboxpopup_btn_delete)
                val outboxButton = popupMenuView.findViewById<Button>(R.id.cboxpopup_btn_out)

                deleteButton.setOnClickListener {
                    Toast.makeText(context, "Delete 버튼 클릭", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }

                outboxButton.setOnClickListener {
                    Toast.makeText(context, "꺼내기 버튼 클릭", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }

                popupWindow.isOutsideTouchable = true

                val location = IntArray(2)
                binding.rvboxclassBtn.getLocationOnScreen(location)

                val x = location[0]-dp100 // x 좌표
                val y = location[1]+dp18 // y 좌표

                popupWindow.showAtLocation(binding.rvboxclassBtn, Gravity.NO_GRAVITY, x, y)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvBoxClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(classlist[position])
    }

    override fun getItemCount() = classlist.size
}