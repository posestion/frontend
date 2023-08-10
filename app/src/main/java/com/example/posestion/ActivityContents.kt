package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.databinding.ActivityContentsBinding

class ActivityContents : AppCompatActivity() {

    private val binding: ActivityContentsBinding by lazy { ActivityContentsBinding.inflate(layoutInflater) }
    private val myDataSet = mutableListOf<DataContents>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AcontentsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerView = binding.AcontentsRv

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = AdapterContents(myDataSet, this@ActivityContents, resources)

        myDataSet.add(DataContents("오마이걸", "효정 금발", "100", "20", R.drawable.h))
        myDataSet.add(DataContents("오마이걸", "효정 흑발", "110", "22", R.drawable.h2))

        recyclerView.adapter?.notifyDataSetChanged()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.contents_search -> {

                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}