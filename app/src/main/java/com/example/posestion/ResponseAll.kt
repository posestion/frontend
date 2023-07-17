package com.example.posestion

import com.google.gson.annotations.SerializedName

data class ResponseAll(
    @SerializedName("user_id")
    val userid: String,
    @SerializedName("password")
    val pw: String,
    @SerializedName("phone_num")
    val phone: String,
    @SerializedName("birth")
    val birth: String?,
    @SerializedName("nickname")
    val nick: String?,
    @SerializedName("username")
    val name: String?
)
