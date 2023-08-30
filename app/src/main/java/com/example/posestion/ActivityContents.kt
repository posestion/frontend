package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.contentslistall
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityContentsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityContents : AppCompatActivity() {

    private val binding: ActivityContentsBinding by lazy { ActivityContentsBinding.inflate(layoutInflater) }
    private lateinit var contentadapter: AdapterContents
    private lateinit var recyclerView: RecyclerView
    private val user = MyApplication.user
    private val token = user.getString("jwt", "").toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val call = RetrofitObject.getRetrofitService.mypagewdyt(token)
        call.enqueue(object : Callback<RetrofitClient.Responsemypagewdyt> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypagewdyt>, response: Response<RetrofitClient.Responsemypagewdyt>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            if(response.result != null){
                                contentslistall = response.result
                                recyclerView = binding.AcontentsRv
                                contentadapter = AdapterContents(contentslistall, this@ActivityContents)

                                val dividerItemDecoration = DividerItemDecoration(this@ActivityContents, LinearLayoutManager.VERTICAL)
                                recyclerView.addItemDecoration(dividerItemDecoration)

                                recyclerView.layoutManager =
                                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                                recyclerView.adapter = contentadapter
                                contentadapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypagewdyt>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        setSupportActionBar(binding.AcontentsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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