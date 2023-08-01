package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Responsepwreset(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?
)
