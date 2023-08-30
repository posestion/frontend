package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardBasicListItemBinding
import timber.log.Timber

class basic_mypick_adapter(private val drawerdibs: List<RetrofitClient.drawerdibs>):RecyclerView.Adapter<basic_mypick_adapter.basic_mypick_ViewHolder>() {
    class basic_mypick_ViewHolder(private val binding: BoardBasicListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart:Int = 0
                fun bind(drawerdibs: RetrofitClient.drawerdibs){
                    heart = drawerdibs.dibs
                    val imageURL = drawerdibs.Image_url
                    binding.boardHotclassTitleTxt.text = drawerdibs.title
                    val image = binding.boardHotclassThumbnail

                    Glide.with(context)
                        .load(imageURL)
                        .into(image)
                    val id = drawerdibs.id

                    if(heart==0){
                        binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_empty_heart)
                    }
                    else if(heart==1){
                        binding.boardHotclassHeart.setImageResource(R.drawable.hotclass_full_heart)
                    }
                    itemView.setOnClickListener {
                        val intent = Intent(context,board_class_view::class.java)
                        intent.putExtra("id",id)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): basic_mypick_ViewHolder {
        Timber.d("onCreateViewHolder")
        return basic_mypick_ViewHolder(
            BoardBasicListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = drawerdibs.size

    override fun onBindViewHolder(holder: basic_mypick_ViewHolder, position: Int) {
        Timber.d("onCreateViewHolder")
        holder.bind(drawerdibs[position])
    }
}

