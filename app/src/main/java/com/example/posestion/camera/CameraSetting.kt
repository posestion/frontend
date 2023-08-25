package com.example.posestion.camera

import android.content.Context
import android.content.SharedPreferences

class CameraSetting(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

//    fun getMarkSwitchStatus(): String {
//        return prefs.getString(key, defValue).toString()
//    }
//
//
//
//    fun setString(key: String, str: String) {
//        prefs.edit().putString(key, str).apply()
//    }
}