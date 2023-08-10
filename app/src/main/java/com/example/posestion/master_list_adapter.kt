package com.example.posestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posestion.databinding.BoardMasterListItemBinding
import timber.log.Timber

class master_list_adapter(private val masterlist: List<master>) : RecyclerView.Adapter<master_list_adapter.master_list_ViewHolder>() {

    class master_list_ViewHolder(val binding : BoardMasterListItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(master: master){
                binding.boardMasterListName.text = master.name
                binding.boardMasterListEx.text = master.master_ex
                binding.boardMasterListRanking.text = master.ranking.toString()
                binding.boardMasterListFollowNum.text = master.follow_num
                binding.boardMasterListFollowIcon.isClickable = master.follow
            }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): master_list_ViewHolder {
        Timber.d("onCreateViewHolder")
        return master_list_ViewHolder(
            BoardMasterListItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = masterlist.size

    override fun onBindViewHolder(holder: master_list_ViewHolder, position: Int) {
        Timber.d("onBindViewHolder")
        val currentMaster = masterlist[position]
        holder.bind(currentMaster)

        holder.binding.boardMasterListFollowIcon.setOnClickListener {
            currentMaster.follow = !currentMaster.follow
            val followIconResource = if (currentMaster.follow) {
                R.drawable.masterlist_following
            } else {
                R.drawable.masterlist_follow
            }
            holder.binding.boardMasterListFollowIcon.setImageResource(followIconResource)
        }
    }

}