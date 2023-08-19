package com.example.posestion.connection

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
    fun getall(): Call<RetrofitClient.ResponseAll>

    //포즈 상점//
    @Multipart
    @POST("/pose/write")
    fun posewrite(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("tag") tags: List<RequestBody>,
        @Part image: MultipartBody.Part
    ): Call<RetrofitClient.PoseWrite>

    @POST("/pose/basket")
    fun posebasket(
        @Part("pose_id") poseid: Int,
    ): Call<RetrofitClient.PoseBasket>

    @GET("/pose/:id")
    fun poseid(
        @Part("id") id: Int,
    ): Call<RetrofitClient.PoseId>

    @GET("/pose/posebasketDelete/:id")
    fun posebasektdelete(
        @Part("id") id: Int,
    ): Call<RetrofitClient.PoseBasketDelete>

    @GET("/pose/allView")
    fun poseallview(): Call<RetrofitClient.PoseAllView>

    @GET("/pose/allBakets")
    fun poseallbasket(): Call<RetrofitClient.PoseAllbasket>

    @GET("/pose/search")
    fun posesearch(
        @Part("word") word: String?,
    ): Call<RetrofitClient.PoseSearch>

    @POST("/pose/addfavorite/80(pose_id)")
    fun poseaddfavorite(): Call<RetrofitClient.PoseAddfavorit>

    @GET("/pose/delfavorite/80(pose_id)")
    fun posedelfavorite(): Call<RetrofitClient.PoseDelfavorite>

    @GET("/pose/favoriteview")
    fun posefavoriteview(): Call<RetrofitClient.PoseFavoriteview>

    @GET("/pose/hotboard")
    fun posehotboard(): Call<RetrofitClient.PoseHotboardResponse>

    @GET("/pose/filterdate")
    fun posefilterdate(): Call<RetrofitClient.PoseFilterdate>

    @GET("/pose/filterpopular")
    fun posefilterpopular(): Call<RetrofitClient.PoseFilterpopular>

    @GET("/pose/delete/:id")
    fun posedeletid(): Call<RetrofitClient.PoseDeleteid>

    @GET("/app/mypage")
    fun mypage(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypage>
}