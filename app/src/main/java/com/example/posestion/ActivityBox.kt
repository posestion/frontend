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
import com.example.posestion.MyApplication.Companion.boxclasslist
import com.example.posestion.MyApplication.Companion.boxcontentslist
import com.example.posestion.MyApplication.Companion.boxphotolist
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityBoxBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityBox : AppCompatActivity() {

    private val binding: ActivityBoxBinding by lazy { ActivityBoxBinding.inflate(layoutInflater) }
    private val user = MyApplication.user
    private val token = user.getString("jwt", "").toString()
    private lateinit var recyclerViewcontents: RecyclerView
    private lateinit var recyclerViewclass: RecyclerView
    private lateinit var recyclerViewphoto: RecyclerView
    private lateinit var classadapter: AdapterMypageClass
    private lateinit var contentsadapter: AdapterMypageContents
    private lateinit var photoadapter: AdapterPhoto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.aboxToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val callwdyt = RetrofitObject.getRetrofitService.mypagestorewdyt(token)
        callwdyt.enqueue(object : Callback<RetrofitClient.Responsemypagewdyt> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypagewdyt>, response: Response<RetrofitClient.Responsemypagewdyt>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if (response != null) {
                        Log.d("Retrofit", response.message)
                        if (response.isSuccess) {
                            if(response.result != null){
                                boxcontentslist = response.result
                                val contentslist = mutableListOf<RetrofitClient.mypageContent>()
                                contentslist.add(boxcontentslist[0])

                                recyclerViewcontents = binding.aboxRvContents
                                contentsadapter = AdapterMypageContents(contentslist, resources, this@ActivityBox)

                                val dividerItemDecoration = DividerItemDecoration(this@ActivityBox, LinearLayoutManager.VERTICAL)
                                recyclerViewcontents.addItemDecoration(dividerItemDecoration)

                                recyclerViewcontents.layoutManager =
                                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                                recyclerViewcontents.adapter = contentsadapter
                                contentsadapter.notifyDataSetChanged()
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

        val callphoto = RetrofitObject.getRetrofitService.mypagestorephoto(token)
        callphoto.enqueue(object : Callback<RetrofitClient.Responsemypagephoto> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypagephoto>, response: Response<RetrofitClient.Responsemypagephoto>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if (response != null) {
                        Log.d("Retrofit", response.message)
                        if (response.isSuccess) {
                            if(response.result != null){
                                boxphotolist = response.result
                                val photolist = mutableListOf<RetrofitClient.mypagephoto>()
                                if(boxphotolist.size <= 3){
                                    for(i in 0..boxphotolist.size){
                                        photolist.add(boxphotolist[i])
                                    }
                                }else{
                                    for(i in 0..2){
                                        photolist.add(boxphotolist[i])
                                    }
                                }
                                recyclerViewphoto = binding.aboxRvPhoto
                                photoadapter = AdapterPhoto(photolist, this@ActivityBox)

                                recyclerViewphoto.layoutManager =
                                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
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

        val callclass = RetrofitObject.getRetrofitService.mypagestoreclass(token)
        callclass.enqueue(object : Callback<RetrofitClient.Responsemypageclass> {
            override fun onResponse(call: Call<RetrofitClient.Responsemypageclass>, response: Response<RetrofitClient.Responsemypageclass>) {
                if (response.isSuccessful) {
                    val response = response.body()
                    if (response != null) {
                        Log.d("Retrofit", response.message)
                        if (response.isSuccess) {
                            if(response.result != null){
                                boxclasslist = response.result
                                val classlist = mutableListOf<RetrofitClient.mypageclass>()
                                if(boxclasslist.size <= 3){
                                    for(i in 0..boxclasslist.size){
                                        classlist.add(boxclasslist[i])
                                    }
                                }else{
                                    for(i in 0..2){
                                        classlist.add(boxclasslist[i])
                                    }
                                }
                                recyclerViewclass = binding.aboxRvClass
                                classadapter = AdapterMypageClass(classlist, resources, this@ActivityBox)

                                recyclerViewclass.layoutManager =
                                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                                recyclerViewclass.adapter = classadapter
                                classadapter.notifyDataSetChanged()
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

        binding.aboxBtnClass.setOnClickListener {
            val intent = Intent(this, ActivityBoxClass::class.java)
            startActivity(intent)
        }

        binding.aboxBtnContents.setOnClickListener {
            val intent = Intent(this, ActivityBoxContents::class.java)
            startActivity(intent)
        }

        binding.aboxBtnPhoto.setOnClickListener {
            val intent = Intent(this, ActivityBoxPhoto::class.java)
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