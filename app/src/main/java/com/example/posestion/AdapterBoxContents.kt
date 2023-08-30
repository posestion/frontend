package com.example.posestion

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.RvContentsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterBoxContents (private val ContentsList: MutableList<RetrofitClient.mypageContent>,
                          private val context: Context
): RecyclerView.Adapter<AdapterBoxContents.viewHolder>() {

    private val user = MyApplication.user
    private val token = user.getString("jwt", "").toString()

    inner class viewHolder(private val binding: RvContentsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : RetrofitClient.mypageContent){
            binding.RvcontentsTitle.text = list.title
            binding.RvcontentsText.text = list.content
            binding.RvcontentsTextHeart.text = list.likenum.toString()
            binding.RvcontentsTextComment.text = list.replynum.toString()

            val pixels = (120 * Resources.getSystem().displayMetrics.density).toInt()
            val dp18 = (18 * Resources.getSystem().displayMetrics.density).toInt()
            val dp100 = (100 * Resources.getSystem().displayMetrics.density).toInt()

            val imageUrl = list.image
            val imageView = binding.RvcontentsImageMain

            Glide.with(context)
                .load(imageUrl)
                .into(imageView)

            binding.RvcontentsImageMain.clipToOutline = true

            binding.RvcontentsBtnMenu.setOnClickListener {
                val popupMenuView = LayoutInflater.from(context).inflate(R.layout.custom_box_popup, null)
                val popupWindow = PopupWindow(popupMenuView, pixels, ViewGroup.LayoutParams.WRAP_CONTENT)

                val deleteButton = popupMenuView.findViewById<Button>(R.id.cboxpopup_btn_delete)
                val outboxButton = popupMenuView.findViewById<Button>(R.id.cboxpopup_btn_out)

                deleteButton.setOnClickListener {
                    val call = RetrofitObject.getRetrofitService.deletepost(token, list.id.toString())
                    call.enqueue(object : Callback<RetrofitClient.Responseusually> {
                        override fun onResponse(call: Call<RetrofitClient.Responseusually>, response: Response<RetrofitClient.Responseusually>) {
                            if (response.isSuccessful) {
                                val response = response.body()
                                if(response != null){
                                    if(response.isSuccess){
                                        ContentsList.removeAt(adapterPosition)
                                        notifyItemRemoved(adapterPosition)
                                        Toast.makeText(context, "이사잘이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<RetrofitClient.Responseusually>, t: Throwable) {
                            val errorMessage = "Call Failed: ${t.message}"
                            Log.d("Retrofit", errorMessage)
                        }
                    })
                    popupWindow.dismiss()
                }

                outboxButton.setOnClickListener {
                    val call = RetrofitObject.getRetrofitService.boxoutpost(token, list.id.toString())
                    call.enqueue(object : Callback<RetrofitClient.Responseusually> {
                        override fun onResponse(call: Call<RetrofitClient.Responseusually>, response: Response<RetrofitClient.Responseusually>) {
                            if (response.isSuccessful) {
                                val response = response.body()
                                if(response != null){
                                    if(response.isSuccess){
                                        ContentsList.removeAt(adapterPosition)
                                        notifyItemRemoved(adapterPosition)
                                        Toast.makeText(context, "이사잘이 보관함에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<RetrofitClient.Responseusually>, t: Throwable) {
                            val errorMessage = "Call Failed: ${t.message}"
                            Log.d("Retrofit", errorMessage)
                        }
                    })
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