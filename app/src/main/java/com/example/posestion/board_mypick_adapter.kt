package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassListItemBinding

class board_mypick_adapter (private val mydibs : List<RetrofitClient.mydibs>):RecyclerView.Adapter<board_mypick_adapter.mypick_viewholder>(){
    class mypick_viewholder(private val binding: BoardClassListItemBinding):RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart:Int = 0
        fun bind(mydibs:RetrofitClient.mydibs){
            heart = mydibs.dibs
            val imageURL = mydibs.Image_url
            binding.boardHotclassTitleTxt.text = mydibs.title
            val image = binding.boardHotclassThumbnail

            Glide.with(context)
                .load(imageURL)
                .into(image)

            if(heart==0){
                binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_empty_heart)
            }
            else if(heart==1){
                binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_full_heart)
            }

            itemView.setOnClickListener {
                val intent = Intent(context,board_class_view::class.java)
                intent.putExtra("title",mydibs.title)
                intent.putExtra("thumbnail",mydibs.Image_url)
                intent.putExtra("heart",heart)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mypick_viewholder {
        return mypick_viewholder(BoardClassListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mydibs.size
    }

    override fun onBindViewHolder(holder: mypick_viewholder, position: Int) {
        holder.bind(mydibs[position])
    }
}