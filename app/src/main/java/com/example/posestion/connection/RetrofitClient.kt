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

    //포즈상점//
    data class PoseWrite(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

    data class PoseBasket(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

    data class PoseId(
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("tag_name")
        val tagname: String?
    )

    data class PoseBasketDelete(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

    data class PoseAllView(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("tag_name")
        val tagname: String?
    )

    data class PoseAllbasket(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String
    )

    data class PoseSearch(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("tag_name")
        val tagname: String?
    )

    data class PoseAddfavorit(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?,
        @SerializedName("result")
        val result: PoseResult
    )

    data class PoseResult(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

    data class PoseDelfavorite(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

    data class PoseFavoriteview(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("tag_name")
        val tagname: String?
    )
    data class PoseHotboardResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<PoseHotboard>
    )
    data class PoseHotboard(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String
    )

    data class PoseFilterdate(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("fav_count")
        val favcount: Int
    )

    data class PoseFilterpopular(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("fav_count")
        val favcount: Int
    )

    data class PoseDeleteid(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )

    data class Responsemypage(
        @SerializedName("token")
        val token: String?
    )
}