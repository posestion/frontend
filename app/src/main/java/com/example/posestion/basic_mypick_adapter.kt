package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardBasicListItemBinding
import timber.log.Timber

class basic_mypick_adapter(private val basic_mypick: List<basic_mypick>):RecyclerView.Adapter<basic_mypick_adapter.basic_mypick_ViewHolder>() {
    class basic_mypick_ViewHolder(private val binding: BoardBasicListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
                fun bind(basic_mypick: basic_mypick){
                    binding.boardHotclassHeart.isClickable = basic_mypick.heart
                    binding.boardHotclassTitleTxt.text = basic_mypick.title
                    binding.boardHotclassThumbnail.setImageResource(basic_mypick.image)

                    itemView.setOnClickListener {
                        val intent = Intent(context,board_class_view::class.java)
                        intent.putExtra("title",basic_mypick.title)
                        intent.putExtra("thumbnail",basic_mypick.image)
                        intent.putExtra("heart",basic_mypick.heart)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): basic_mypick_ViewHolder {
        Timber.d("onCreateViewHolder")
        return basic_mypick_ViewHolder(
            BoardBasicListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: basic_mypick_ViewHolder, position: Int) {
        Timber.d("onCreateViewHolder")
        holder.bind(basic_mypick[position])
    }
}

