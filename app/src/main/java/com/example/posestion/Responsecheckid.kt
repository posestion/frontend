package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Responsecheckid(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: List<CheckidResult>
)

data class CheckidResult(
    @SerializedName("user_id")
    val userid: String
)
