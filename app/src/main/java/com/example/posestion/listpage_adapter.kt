package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardListpageListItemBinding
import timber.log.Timber

class listpage_adapter(private val listpagelist: List<RetrofitClient.wdytlist>) :
    RecyclerView.Adapter<listpage_adapter.listpage_ViewHolder>() {

    class listpage_ViewHolder(val binding: BoardListpageListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        var heart = 0
                fun bind(listpage: RetrofitClient.wdytlist){

                    binding.boardListpageListTitle.text = listpage.title
                    binding.boardListpageListHeartnum.text = listpage.Number_of_like.toString()
                    binding.boardListpageListCommentnum.text = listpage.Number_of_reply.toString()
                    heart = listpage.like
                    val imageURL = listpage.Image_url
                    val image = binding.boardListpageListPhoto

                    Glide.with(context)
                        .load(imageURL)
                        .into(image)

                    if(heart==0){
                        binding.boardListpageListHeart.setImageResource(R.drawable.list_empty_heart)
                    }
                    else if(heart==1){
                        binding.boardListpageListHeart.setImageResource(R.drawable.list_full_heart)
                    }

                    itemView.setOnClickListener {
                        val intent = Intent(context,board_contents_view::class.java)
                        intent.run { context.startActivity(this) }
                    }
                }
            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listpage_ViewHolder {
        Timber.d("onCreateViewHolder")
        return listpage_ViewHolder(
            BoardListpageListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false,
            )
        )
    }

    override fun getItemCount(): Int = listpagelist.size

    override fun onBindViewHolder(holder: listpage_ViewHolder, position: Int) {
        Timber.d("onBindeViewHolder")
        val currentlist = listpagelist[position]
        holder.bind(currentlist)

        holder.binding.boardListpageListHeart.setOnClickListener {
            if(holder.heart==0) {
                holder.heart=1
                currentlist.Number_of_like++
                holder.binding.boardListpageListHeartnum.text = currentlist.Number_of_like.toString()
                holder.binding.boardListpageListHeart.setImageResource(R.drawable.list_full_heart)
            }
            else if (holder.heart==1) {
                holder.heart=0
                currentlist.Number_of_like--
                holder.binding.boardListpageListHeartnum.text = currentlist.Number_of_like.toString()
                holder.binding.boardListpageListHeart.setImageResource(R.drawable.list_empty_heart)
            }

        }


    }
}