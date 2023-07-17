package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Requestlogin(
    @SerializedName("user_id")
    val usereid: String,
    @SerializedName("password")
    val userpassword: String
)
