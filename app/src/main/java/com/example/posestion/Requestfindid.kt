package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Requestfindid(
    @SerializedName("username")
    val username: String,
    @SerializedName("phone_num")
    val phonenum: String
)
