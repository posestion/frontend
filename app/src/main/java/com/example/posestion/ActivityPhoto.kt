package com.example.posestion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.photolistall
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityPhotoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityPhoto : AppCompatActivity() {

    private val binding: ActivityPhotoBinding by lazy { ActivityPhotoBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val token = user.getString("jwt", "").toString()
    private lateinit var recyclerViewphoto: RecyclerView
    private lateinit var photoadapter: AdapterPhoto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.aphotoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val call = RetrofitObject.getRetrofitService.mypagephoto(token)
        call.enqueue(object : Callback<RetrofitClient.Responsemypagephoto> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypagephoto>, response: Response<RetrofitClient.Responsemypagephoto>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            if(response.result != null){
                                photolistall = response.result
                                recyclerViewphoto = binding.aphotoRv
                                photoadapter = AdapterPhoto(photolistall, this@ActivityPhoto)

                                recyclerViewphoto.layoutManager =
                                    StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                                recyclerViewphoto.adapter = photoadapter
                                photoadapter.notifyDataSetChanged()
                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypagephoto>, t: Throwable) {
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