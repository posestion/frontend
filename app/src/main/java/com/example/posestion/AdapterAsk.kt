package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.MyApplication.Companion.filecount
import com.example.posestion.databinding.RvAskBinding
import okhttp3.MultipartBody
import java.io.File

class AdapterAsk(
    private val filenamelist : MutableList<DataFile>,
    private val filelist: MutableList<File>
) : RecyclerView.Adapter<AdapterAsk.viewHolder>() {

    inner class viewHolder(private val binding: RvAskBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(list : DataFile) {
            binding.rvaskTitle.text = list.title
            binding.rvaskDelete.setOnClickListener {
                filenamelist.removeAt(adapterPosition)
                filelist.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                filecount--
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvAskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterAsk.viewHolder, position: Int) {
        holder.bind(filenamelist[position])
    }

    override fun getItemCount() = filenamelist.size
}