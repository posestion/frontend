package com.example.posestion.connection

import com.google.gson.annotations.SerializedName

class RetrofitClient {
    data class Requestlogin(
        @SerializedName("user_id")
        val usereid: String,
        @SerializedName("password")
        val userpassword: String
    )

    data class Responselogin(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: LoginResult
    )

    data class LoginResult(
        @SerializedName("userId")
        val userid: String,
        @SerializedName("jwt")
        val jwt: String
    )

    data class Requestfindid(
        @SerializedName("username")
        val username: String,
        @SerializedName("phone_num")
        val phonenum: String
    )

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

    data class Responsepwreset(
        @SerializedName("isSuccess")
        val isSuccess: Boolean?,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

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

    data class ResponseSignup(
        @SerializedName("isSuccess")
        val isSuccess: Boolean?,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

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
}