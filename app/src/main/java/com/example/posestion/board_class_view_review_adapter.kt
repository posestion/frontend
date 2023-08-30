package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.BoardClassViewReviewListItemBinding

class board_class_view_review_adapter(private val review: List<RetrofitClient.classdetailreviews>) : RecyclerView.Adapter<board_class_view_review_adapter.board_class_view_review_viewholder>() {

    class board_class_view_review_viewholder(private val binding: BoardClassViewReviewListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review : review){
            binding.boardClassViewReviewName.text = review.name
            binding.boardClassViewReviewDate.text = review.date
            binding.boardClassViewReviewContents.text = review.contents
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): board_class_view_review_viewholder {
        return board_class_view_review_viewholder(BoardClassViewReviewListItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return review.size
    }

    override fun onBindViewHolder(holder: board_class_view_review_viewholder, position: Int) {
        holder.bind(review[position])
    }
}