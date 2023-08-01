package com.example.posestion

import com.google.gson.annotations.SerializedName

data class ResponseSignup(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?
)

