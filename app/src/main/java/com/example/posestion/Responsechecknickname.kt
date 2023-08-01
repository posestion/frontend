package com.example.posestion

import com.google.gson.annotations.SerializedName

data class Responsenickname(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: List<NicknameResult>
)

data class NicknameResult(
    @SerializedName("nickname")
    val nickname: String
)
