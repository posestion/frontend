package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Requestpwreset(
    @SerializedName("user_id")
    val userid: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("phone_num")
    val phonenum: String,
    @SerializedName("password")
    val userpw: String
)
