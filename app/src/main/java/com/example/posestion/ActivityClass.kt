package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.databinding.ActivityClassBinding

class ActivityClass : AppCompatActivity() {

    private val binding: ActivityClassBinding by lazy { ActivityClassBinding.inflate(layoutInflater) }
    private val myDataSet = mutableListOf<DataClass>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AclassToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerView = binding.AclassRv

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = AdapterClass(myDataSet, resources)

        myDataSet.add(DataClass("오마이걸 효정 금발", R.drawable.h))
        myDataSet.add(DataClass("오마이걸 효정 흑발", R.drawable.h2))
        myDataSet.add(DataClass("엔믹스 혜원", R.drawable.h3))

        recyclerView.adapter?.notifyDataSetChanged()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.class_search -> {

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