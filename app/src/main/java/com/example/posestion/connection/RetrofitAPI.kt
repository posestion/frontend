package com.example.posestion.connection

import android.content.SharedPreferences
import com.example.posestion.MyApplication
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RetrofitAPI {

    //회원가입
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

    //로그인
    @POST("/app/login")
    fun login(@Body request: RetrofitClient.Requestlogin): Call<RetrofitClient.Responselogin>

    //아이디 찾기
    @POST("/app/users/findId")
    fun findid(@Body request: RetrofitClient.Requestfindid): Call<RetrofitClient.Responsefindid>

    //비밀번호 초기화
    @PATCH("/app/users/pwReset")
    fun resetpw(@Body request: RetrofitClient.Requestpwreset): Call<RetrofitClient.Responsepwreset>

    //아이디 중복확인
    @GET("/app/users/checkid/{id}")
    fun checkid(
        @Path("id") id: String
    ): Call<RetrofitClient.Responsecheckid>

    //닉네임 중복확인
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
    fun posebasket(@Body requestBody: RetrofitClient.PoseRequestBody): Call<RetrofitClient.PoseBasket>

    @GET("/pose/:id")
    fun poseid(
        @Part("id") id: Int,
    ): Call<RetrofitClient.PoseId>

    @GET("/pose/posebasketDelete/{id}")
    fun posebasektdelete(
        @Path("id") id: Int,
    ): Call<RetrofitClient.PoseBasketDelete>

    @GET("/pose/allView")
    fun poseallview(): Call<RetrofitClient.PoseAllView>

    @GET("/pose/allBakets")
    fun poseallbasket(): Call<RetrofitClient.PoseAllbasket>

    @GET("/pose/search")
    fun posesearch( @Query("word") word: String?=null): Call<RetrofitClient.PoseSearchResponse>

    @GET("/pose/searchHot")
    fun posesearchhot( @Query("word") word: String?=null): Call<RetrofitClient.PoseSearchHotResponse>

    @POST("/pose/addfavorite/{pose_id}")
    fun poseaddfavorite(@Path("pose_id") poseId: Int): Call<RetrofitClient.PoseAddfavoriteResponse>

    @GET("/pose/delfavorite/{pose_id}")
    fun posedelfavorite(@Path("pose_id") poseId: Int): Call<RetrofitClient.PoseDelfavorite>

    @GET("/pose/favoriteview")
    fun posefavoriteview(): Call<RetrofitClient.PoseFavoriteviewResponse>

    @GET("/pose/hotboard")
    fun posehotboard(): Call<RetrofitClient.PoseHotboardResponse>

    @GET("/pose/ageNewest")
    fun posefilterdate(): Call<RetrofitClient.PoseFilterdateResponse>

    @GET("/pose/agePopular")
    fun posefilterpopular(): Call<RetrofitClient.PoseFilterpopularResponse>

    @GET("/pose/delete/:id")
    fun posedeletid(): Call<RetrofitClient.PoseDeleteid>

    //내가 올린 컨텐츠
    @GET("/app/profile/{nickname}/content")
    fun mycontents(
        @Header("x-access-token") token: String,
        @Path("nickname") nickname: String
    ): Call<RetrofitClient.ResponsemyContent>

    //내가 올린 강의
    @GET("/app/profile/{nickname}/class")
    fun myclass(
        @Header("x-access-token") token: String,
        @Path("nickname") nickname: String
    ): Call<RetrofitClient.ResponsemyClass>

    //마이페이지
    @GET("/app/mypage")
    fun mypage(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypage>

    @GET("/pose/getAge")
    fun posegetage(): Call<RetrofitClient.PoseGetageResponse>
  
    //이사잘 삭제
    @GET("/app/wdyt/delete/{id}")
    fun deletepost(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.ResponseDeletepost>

    //문의하기
    @Multipart
    @POST("/app/cs/inquiry")
    fun ask(
        @Header("x-access-token") token: String,
        @Part("title") marketingAgreement: RequestBody,
        @Part("content") userId: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<RetrofitClient.ResponseAsk>

    @GET("/app/cs/inquiry")
    fun myask(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.ResponsemyAsk>
}