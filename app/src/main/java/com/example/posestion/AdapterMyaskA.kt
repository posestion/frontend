package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.RvAnsweroBinding

class AdapterMyaskA(private val answerlist: List<RetrofitClient.answero>): RecyclerView.Adapter<AdapterMyaskA.viewHolder>() {

    inner class viewHolder(private val binding: RvAnsweroBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : RetrofitClient.answero) {
            binding.rvansweroTitle.text = list.title
            binding.rvansweroName.text = list.ansname
            binding.rvansweroAnswer.text = list.anscontent
            binding.rvansweroContent.text = list.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvAnsweroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterMyaskA.viewHolder, position: Int) {
        holder.bind(answerlist[position])
    }

    override fun getItemCount() = answerlist.size
}