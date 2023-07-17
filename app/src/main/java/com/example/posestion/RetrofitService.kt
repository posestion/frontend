package com.example.posestion

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RetrofitService {

    @Multipart
    @POST("/app/users")
    fun signup(
        @Part marketing_agreement: MultipartBody.Part?,
        @Part user_id: MultipartBody.Part?,
        @Part password: MultipartBody.Part?,
        @Part phone_num: MultipartBody.Part?,
        @Part birth: MultipartBody.Part?,
        @Part nickname: MultipartBody.Part?,
        @Part username: MultipartBody.Part?,
        @Part imageFile: MultipartBody.Part?
    ): Call<ResponseSignup>

    @POST("/app/login")
    fun login(@Body request:Requestlogin): Call<Responselogin>

    @POST("/app/users/find/id")
    fun findid(@Body request:Requestfindid): Call<Responsefindid>

    @PATCH("/app/users/find/pwReset")
    fun findpw(@Body request:Requestlogin): Call<ResponseSignup>

    @GET("/app/users/checkid/{id}")
    fun checkid(
        @Path("id") id: String
    ): Call<Responsecheckid>

    @GET("/app/users/checkname/{nickname}")
    fun checknickname(
        @Path("nickname") nickname: String
    ): Call<Responsenickname>

    @GET("/app/getAllUsers")
    fun getall(): Call<List<ResponseAll>>
}