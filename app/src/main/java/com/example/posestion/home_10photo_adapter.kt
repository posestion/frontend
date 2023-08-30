package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardHome10photoListItemBinding

class home_10photo_adapter(private val home_10photo: List<RetrofitClient.TensPhoto>):RecyclerView.Adapter<home_10photo_adapter.home_10photo_viewholder>() {
    class home_10photo_viewholder(private val binding: BoardHome10photoListItemBinding):RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(home_10photo : RetrofitClient.TensPhoto){

            val imageURL = home_10photo.poseImage
            binding.home10photoTitle.text = home_10photo.title
            val image = binding.boardHome10photoImage
            binding.home10photoJob.text = home_10photo.nickname

            Glide.with(context)
                .load(imageURL)
                .into(image)

            itemView.setOnClickListener {
                val intent = Intent(context,board_10photo::class.java)
                intent.putExtra("title",home_10photo.title)
                intent.putExtra("job",home_10photo.nickname)
                intent.putExtra("photo",home_10photo.poseImage)
                intent.run { context.startActivity(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): home_10photo_viewholder {
        return home_10photo_viewholder(BoardHome10photoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = home_10photo.size

    override fun onBindViewHolder(holder: home_10photo_viewholder, position: Int) {
        holder.bind(home_10photo[position])
    }
}