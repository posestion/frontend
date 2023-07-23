package com.example.posestion

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RetrofitService {

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
    ): Call<ResponseSignup>

    @POST("/app/login")
    fun login(@Body request:Requestlogin): Call<Responselogin>

    @POST("/app/users/findId")
    fun findid(@Body request:Requestfindid): Call<Responsefindid>

    @PATCH("/app/users/find/pwReset")
    fun resetpw(@Body request:Requestpwreset): Call<Responsepwreset>

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