package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.MyApplication.Companion.contentslist
import com.example.posestion.MyApplication.Companion.photolist
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityMyContentsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMyContents : AppCompatActivity() {

    private val binding: ActivityMyContentsBinding by lazy { ActivityMyContentsBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val token = user.getString("jwt", "").toString()
    private lateinit var recyclerViewcontents: RecyclerView
    private lateinit var recyclerViewphoto: RecyclerView
    private lateinit var contentsadapter: AdapterMypageContents
    private lateinit var photoadapter: AdapterPhoto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val call = RetrofitObject.getRetrofitService.mypagecontents(token)
        call.enqueue(object : Callback<RetrofitClient.Responsemypagecontents> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypagecontents>, response: Response<RetrofitClient.Responsemypagecontents>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if(response != null){
                        if(response.isSuccess){
                            val result = response.result
                            if(result.photo != null){
                                photolist = result.photo
                                recyclerViewphoto = binding.amycontentsRvPhoto
                                photoadapter = AdapterPhoto(photolist, this@ActivityMyContents)

                                recyclerViewphoto.layoutManager =
                                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                                recyclerViewphoto.adapter = photoadapter
                                photoadapter.notifyDataSetChanged()
                            }
                            if(result.wdyt != null){
                                contentslist = result.wdyt
                                recyclerViewcontents = binding.amycontentsRvContents
                                contentsadapter = AdapterMypageContents(contentslist, resources,this@ActivityMyContents)

                                recyclerViewcontents.layoutManager =
                                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                                recyclerViewcontents.adapter = contentsadapter
                                contentsadapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient.Responsemypagecontents>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit", errorMessage)
            }
        })

        binding.amycontentsBtnContent.setOnClickListener {
            val intent = Intent(this, ActivityContents::class.java)
            startActivity(intent)
        }

        binding.amycontentsBtnPhoto.setOnClickListener {
            val intent = Intent(this, ActivityPhoto::class.java)
            startActivity(intent)
        }
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