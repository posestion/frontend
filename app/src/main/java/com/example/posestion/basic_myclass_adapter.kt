package com.example.posestion

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardBasicListItemBinding
import timber.log.Timber

class basic_myclass_adapter(private val basic_myclass: List<basic_myclass>) : RecyclerView.Adapter<basic_myclass_adapter.basic_myclass_ViewHolder>(){
    class basic_myclass_ViewHolder(private val binding: BoardBasicListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
                fun bind(basic_myclass: basic_myclass){
                    binding.boardHotclassHeart.isClickable = basic_myclass.heart
                    binding.boardHotclassTitleTxt.text = basic_myclass.title
                    binding.boardHotclassThumbnail.setImageResource(basic_myclass.image)

                    itemView.setOnClickListener {
                        val intent = Intent(context,board_class_view::class.java)
                        intent.putExtra("title",basic_myclass.title)
                        intent.putExtra("thumbnail",basic_myclass.image)
                        intent.putExtra("heart",basic_myclass.heart)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): basic_myclass_ViewHolder {
        Timber.d("onCreateViewHolder")
        return basic_myclass_ViewHolder(
            BoardBasicListItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: basic_myclass_ViewHolder, position: Int) {
        Timber.d("onCreateViewHolder")
        holder.bind(basic_myclass[position])
    }
}