package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardListpageListItemBinding
import timber.log.Timber

class listpage_adapter(private val listpagelist:List<listpage>) :
    RecyclerView.Adapter<listpage_adapter.listpage_ViewHolder>() {

    class listpage_ViewHolder(val binding: BoardListpageListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
                fun bind(listpage: listpage){
                    binding.boardListpageListTitle.text = listpage.title
                    binding.boardListpageListEx.text = listpage.page_ex
                    binding.boardListpageListHeart.isClickable = listpage.heart
                    binding.boardListpageListHeartnum.text = listpage.heart_num.toString()
                    binding.boardListpageListCommentnum.text = listpage.comment_num.toString()

                    itemView.setOnClickListener {
                        val intent = Intent(context,board_contents_view::class.java)
                        intent.putExtra("titletext",listpage.title)
                        intent.putExtra("contentstext",listpage.page_ex)
                        intent.putExtra("heart",listpage.heart)
                        intent.putExtra("heartcount",listpage.heart_num)
                        intent.putExtra("commentcount",listpage.comment_num)
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
            currentlist.heart = !currentlist.heart

            val heartIconResource = if(currentlist.heart){
                currentlist.heart_num++
                holder.binding.boardListpageListHeartnum.text = currentlist.heart_num.toString()
                R.drawable.list_full_heart
            }
            else{
                currentlist.heart_num--
                holder.binding.boardListpageListHeartnum.text = currentlist.heart_num.toString()
                R.drawable.list_empty_heart
            }
            holder.binding.boardListpageListHeart.setImageResource(heartIconResource)

        }


    }
}