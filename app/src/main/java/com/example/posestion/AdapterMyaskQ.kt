package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.RvAnswerxBinding

class AdapterMyaskQ(private val asklist: List<RetrofitClient.answerx>): RecyclerView.Adapter<AdapterMyaskQ.viewHolder>() {

    inner class viewHolder(private val binding: RvAnswerxBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : RetrofitClient.answerx) {
            binding.rvanswerxTitle.text = list.title
            binding.rvanswerxText.text = list.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvAnswerxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterMyaskQ.viewHolder, position: Int) {
        holder.bind(asklist[position])
    }

    override fun getItemCount() = asklist.size
}