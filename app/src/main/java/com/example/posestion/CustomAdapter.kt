package com.example.posestion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber.Forest.tag

class CustomAdapter(    private val sharedViewModel: SharedViewModel,
                        private val retrofitServiceWithToken: RetrofitAPI,
                        private val lifecycleOwner: LifecycleOwner,
                        private val showLargeImageDialog: (String, String, String,Int) -> Unit
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val getAgeDataList = mutableListOf<RetrofitClient.PoseGetage>()

    fun setData(dataList: List<RetrofitClient.PoseGetage>) { // 수정: PoseGetage로 변경
        getAgeDataList.clear()
        getAgeDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment2_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getAgeDataList[position]
        holder.bind(item)

        holder.tvMain.text = (position + 1).toString()

        if (holder.textTag!= null) {
            val tagsText = item.tagnames?.filterNotNull()?.joinToString(", ") { tag -> "#$tag" } ?: ""
            holder.textTag.text = tagsText
        } else {
            holder.textTag.text = ""
        }
    }

    override fun getItemCount(): Int = getAgeDataList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val imageButton: ImageButton = itemView.findViewById(R.id.imageButton1)
        val tvMain: TextView = itemView.findViewById(R.id.tv_main)
        val textTag: TextView = itemView.findViewById(R.id.tagText1)
        private var isButtonFilled = false

        init {
            imageButton.setBackgroundResource(R.drawable._icon__heart_)


            imageButton.setOnClickListener {
                isButtonFilled = !isButtonFilled
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val getage = getAgeDataList[position]
                    if (isButtonFilled) {
                        imageButton.setBackgroundResource(R.drawable.fillheart)
                        onHeartButtonClick(getage.id, getage.poseImage, getage.tagnames)
                        Log.d("HeartButtonClick1", getage.id.toString())
                    } else {
                        imageButton.setBackgroundResource(R.drawable._icon__heart_)
                        Log.d("HeartButtonClick", "Canceled Pose ID: ${getage.id}")
                        sharedViewModel.deleteImage(getage.id)
                    }
                }
            }

            // RemovedImage를 옵저빙
            sharedViewModel.removedImage.observe(lifecycleOwner) { removedImageId ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val getage = getAgeDataList[position]
                    if (removedImageId == getage.id) {
                        imageButton.setBackgroundResource(R.drawable._icon__heart_)
                        isButtonFilled = false
                    }
                }
            }

            imageView.setOnClickListener{
                val item = getAgeDataList[adapterPosition]
                val imageUrl = item.poseImage
                val imageId = item.id
                val title = item.title
                val content = item.content
                val poseId= item.poseId
                showLargeImageDialog(imageUrl, title, content,poseId)
            }

        }

        fun bind(item: RetrofitClient.PoseGetage) {
            Glide.with(itemView.context)
                .load(item.poseImage)
                .centerCrop()
                .into(imageView)
        }
    }
    private fun onHeartButtonClick(imageId: Int,imageUrl: String, tagNames: List<String>?) {
        Log.d(imageId.toString(),"bbb")

        retrofitServiceWithToken.poseaddfavorite(imageId).enqueue(object :
            Callback<RetrofitClient.PoseAddfavoriteResponse> {
            override fun onResponse(
                call: Call<RetrofitClient.PoseAddfavoriteResponse>,
                response: Response<RetrofitClient.PoseAddfavoriteResponse>
            ) {
                if (response.isSuccessful) {
                    sharedViewModel.addNewImage(imageId)
                    sharedViewModel.addImageUrl(imageId, imageUrl, tagNames)
                }
            }
            override fun onFailure(call: Call<RetrofitClient.PoseAddfavoriteResponse>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit23", errorMessage)
            }
        })
    }
    private class DiffCallback : DiffUtil.ItemCallback<RetrofitClient.PoseSearch>() {
        override fun areItemsTheSame(oldItem: RetrofitClient.PoseSearch, newItem: RetrofitClient.PoseSearch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RetrofitClient.PoseSearch, newItem: RetrofitClient.PoseSearch): Boolean {
            return oldItem == newItem
        }
    }
}
