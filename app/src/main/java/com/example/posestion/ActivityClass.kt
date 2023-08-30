package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.classlistall
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityClassBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityClass : AppCompatActivity() {

    private val binding: ActivityClassBinding by lazy { ActivityClassBinding.inflate(layoutInflater) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var classadapter: AdapterClass
    private val user = MyApplication.user
    private val token = user.getString("jwt", "").toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AclassToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val call = RetrofitObject.getRetrofitService.mypageclass(token)
        call.enqueue(object : Callback<RetrofitClient.Responsemypageclass> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypageclass>, response: Response<RetrofitClient.Responsemypageclass>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if (response != null) {
                        Log.d("Retrofit", response.message)
                        if (response.isSuccess) {
                            if(response.result != null){
                                classlistall = response.result
                                recyclerView = binding.AclassRv
                                classadapter = AdapterClass(classlistall, resources, this@ActivityClass)
                                recyclerView.layoutManager =
                                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                                recyclerView.adapter = classadapter

                                recyclerView.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypageclass>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })
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