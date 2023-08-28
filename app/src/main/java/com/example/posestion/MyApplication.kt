package com.example.posestion

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.posestion.connection.RetrofitClient

class MyApplication : Application() {
    companion object {
        lateinit var user: SharedPreferences
        var classlist = mutableListOf<RetrofitClient.mypageclass>()
        var contentslist = mutableListOf<RetrofitClient.mypageContent>()
        var poselist = mutableListOf<RetrofitClient.mypageposeDrawer>()
        var classlistall = mutableListOf<RetrofitClient.mypageclass>()
        var contentslistall = mutableListOf<RetrofitClient.mypageContent>()
        var photolist = mutableListOf<RetrofitClient.mypagephoto>()
        var photolistall = mutableListOf<RetrofitClient.mypagephoto>()
        var boxcontentslist = mutableListOf<RetrofitClient.mypageContent>()
        var boxphotolist = mutableListOf<RetrofitClient.mypagephoto>()
        var boxclasslist = mutableListOf<RetrofitClient.mypageclass>()
        var filecount = 0
    }

    override fun onCreate() {
        super.onCreate()
        user = getSharedPreferences("user", Context.MODE_PRIVATE)
    }
}