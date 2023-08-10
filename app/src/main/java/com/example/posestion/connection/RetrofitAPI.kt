package com.example.posestion.connection

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RetrofitAPI {

    @Multipart
    @POST("/app/users")
    fun signup(
        @Part("marketing_agreement") marketingAgreement: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_num") phoneNumber: RequestBody,
        @Part("birth") birth: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("username") username: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<RetrofitClient.ResponseSignup>

    @POST("/app/login")
    fun login(@Body request: RetrofitClient.Requestlogin): Call<RetrofitClient.Responselogin>

    @POST("/app/users/findId")
    fun findid(@Body request: RetrofitClient.Requestfindid): Call<RetrofitClient.Responsefindid>

    @PATCH("/app/users/pwReset")
    fun resetpw(@Body request: RetrofitClient.Requestpwreset): Call<RetrofitClient.Responsepwreset>

    @GET("/app/users/checkid/{id}")
    fun checkid(
        @Path("id") id: String
    ): Call<RetrofitClient.Responsecheckid>

    @GET("/app/users/checkname/{nickname}")
    fun checknickname(
        @Path("nickname") nickname: String
    ): Call<RetrofitClient.Responsenickname>

    @GET("/app/getAllUsers")
    fun getall(): Call<List<RetrofitClient.ResponseAll>>
}