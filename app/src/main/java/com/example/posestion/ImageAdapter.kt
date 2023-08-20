package com.example.posestion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.reflect.KFunction0

class ImageAdapter(private var imageList: MutableList<Int> = mutableListOf(), private val sharedViewModel: SharedViewModel,  private val showLargeImageDialog: (Int) -> Unit ) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        Log.d("Image ID66", itemView.toString())
        return ViewHolder(itemView)
    }

    internal val isButtonFilledList: MutableList<Boolean> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Image ID44", position.toString())
        val imageId = imageList[position]

        val imageUrl = sharedViewModel.getImageUrlForId(imageId) // 이미지 아이디에 해당하는 URL 가져오기
        val imageTags = sharedViewModel.getImageTagsForId(imageId)
        // 이미지 URL을 Glide로 로드하여 이미지뷰에 표시합니다.
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .centerCrop()
            .into(holder.imageView)


        holder.tvMain.text = (position + 1).toString()
        Log.d("Image ID33", imageId.toString())
        holder.imageId = imageId // 이미지 ID를 ViewHolder에 저장합니다

        holder.textTag.text = imageTags?.joinToString(", ")

        holder.imageView.setOnClickListener{
            val imageId = imageList[position]
            showLargeImageDialog(imageId) // 이미지 클릭 시 다이얼로그 표시
        }

        holder.isButtonFilled = isButtonFilledList.getOrNull(position) == true

        holder.imageButton1.setOnClickListener {
            // Check if the imageList is not empty
            if (imageList.isNotEmpty() && isButtonFilledList.isNotEmpty() && position in 0 until imageList.size) {
                val imageIdToRemove = imageList[position]
                Log.d("HeartButtonClick2", imageIdToRemove.toString())
                // Remove items from both lists
                imageList.removeAt(position)
                isButtonFilledList.removeAt(position)

                // Update the adapter to reflect the changes
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount - position) // Adjusted range

                // Notify the sharedViewModel about the removed image ID
                sharedViewModel.removeImage(imageIdToRemove)
                sharedViewModel.deleteImage(imageIdToRemove)

                Log.d("Image List2", imageList.toString())

                // Notify the sharedViewModel about the removed image ID
                sharedViewModel.notifyImageRemoved(imageIdToRemove)
                Log.d("Image ID13", sharedViewModel.notifyImageRemoved(imageIdToRemove).toString())
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvMain: TextView = itemView.findViewById(R.id.tv_main)
        val textTag: TextView = itemView.findViewById(R.id.tagText1)
        val imageButton1: ImageButton = itemView.findViewById(R.id.imageButton1)
        var imageId: Int = 0 // 추가: 이미지 ID를 저장할 변수
        var isButtonFilled: Boolean = false
    }

    fun updateData(newImageList: List<Int>) {
        imageList.clear()
        imageList.addAll(newImageList)
        Log.d("Image List", imageList.toString())
        // Update the button state list to match the new image list size
        isButtonFilledList.clear()
        for (i in 0 until newImageList.size) {
            isButtonFilledList.add(false)
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun getImageList(): MutableList<Int> {
        return imageList
    }

}