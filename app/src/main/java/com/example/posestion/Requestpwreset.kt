package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Requestpwreset(
    @SerializedName("usernmae")
    val username: String,
    @SerializedName("phone_num")
    val phonenum: String
)
