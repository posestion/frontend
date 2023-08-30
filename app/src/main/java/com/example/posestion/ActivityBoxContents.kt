package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.boxcontentslist
import com.example.posestion.databinding.ActivityBoxContentsBinding

class ActivityBoxContents : AppCompatActivity() {

    private val binding: ActivityBoxContentsBinding by lazy { ActivityBoxContentsBinding.inflate(layoutInflater) }
    private lateinit var recyclerViewcontents: RecyclerView
    private lateinit var contentsadapter: AdapterBoxContents

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.aboxcontentsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        recyclerViewcontents = binding.aboxcontentsRv
        contentsadapter = AdapterBoxContents(boxcontentslist, this)

        recyclerViewcontents.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewcontents.adapter = contentsadapter
        contentsadapter.notifyDataSetChanged()
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