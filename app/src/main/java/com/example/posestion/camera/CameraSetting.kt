package com.example.posestion.camera

import android.content.Context
import android.content.SharedPreferences

class CameraSetting(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("camera_setting", Context.MODE_PRIVATE)

    fun getMarkSwitchStatus(): Boolean {
        return prefs.getBoolean("mark_switch", false)
    }

    fun setMarkSwitchStatus(stat: Boolean) {
        prefs.edit().putBoolean("mark_switch", stat).apply()
    }

    fun getMarkDateStatus(): Boolean {
        return prefs.getBoolean("mark_date_switch", false)
    }

    fun setMarkDateStatus(stat: Boolean) {
        prefs.edit().putBoolean("mark_date_switch", stat).apply()
    }

    fun getMarkNicknameStatus(): Boolean {
        return prefs.getBoolean("mark_nickname_switch", false)
    }

    fun setMarkNicknameStatus(stat: Boolean) {
        prefs.edit().putBoolean("mark_nickname_switch", stat).apply()
    }

    fun getMarkNicknameString(): String {
        return prefs.getString("mark_nickname_string", "").toString()
    }

    fun setMarkNicknameString(stat: String) {
        prefs.edit().putString("mark_nickname_string", stat).apply()
    }

    fun getLineSwitchStatus(): Boolean {
        return prefs.getBoolean("line_switch", false)
    }

    fun setLineSwitchStatus(stat: Boolean) {
        prefs.edit().putBoolean("line_switch", stat).apply()
    }
}