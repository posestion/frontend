package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Responsefindid(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: FindidResult
)

data class FindidResult(
    @SerializedName("user_id")
    val userid: String,
)

