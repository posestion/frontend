package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Responselogin(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: List<LoginResult>
)

data class LoginResult(
    @SerializedName("userId")
    val userid: String,
    @SerializedName("jwt")
    val jwt: String
)