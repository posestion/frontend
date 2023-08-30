package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.boxclasslist
import com.example.posestion.databinding.ActivityBoxClassBinding

class ActivityBoxClass : AppCompatActivity() {

    private val binding: ActivityBoxClassBinding by lazy { ActivityBoxClassBinding.inflate(layoutInflater) }
    private lateinit var recyclerViewclass: RecyclerView
    private lateinit var classadapter: AdapterBoxClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.aboxclassToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerViewclass = binding.aboxclassRv
        classadapter = AdapterBoxClass(boxclasslist, resources, this)

        recyclerViewclass.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewclass.adapter = classadapter
        classadapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {

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