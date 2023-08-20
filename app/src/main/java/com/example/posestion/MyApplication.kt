package com.example.posestion

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.posestion.connection.RetrofitClient

class MyApplication : Application() {
    companion object {
        lateinit var user: SharedPreferences
        var classlist = listOf<RetrofitClient.myClass>()
        var filecount = 0
    }

    override fun onCreate() {
        super.onCreate()
        user = getSharedPreferences("user", Context.MODE_PRIVATE)
    }
}