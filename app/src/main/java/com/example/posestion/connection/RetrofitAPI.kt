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
        @Part("introduction") introduction: RequestBody?,
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
    fun posesearch( @Query("word") word: String?=null): Call<RetrofitClient.PoseSearchResponse>

    @POST("/pose/addfavorite/{pose_id}")
    fun poseaddfavorite(@Path("pose_id") poseId: Int): Call<RetrofitClient.PoseAddfavoriteResponse>

    @GET("/pose/delfavorite/{pose_id}")
    fun posedelfavorite(@Path("pose_id") poseId: Int): Call<RetrofitClient.PoseDelfavorite>

    @GET("/pose/favoriteview")
    fun posefavoriteview(): Call<RetrofitClient.PoseFavoriteviewResponse>

    @GET("/pose/hotboard")
    fun posehotboard(): Call<RetrofitClient.PoseHotboardResponse>

    @GET("/pose/filterdate")
    fun posefilterdate(): Call<RetrofitClient.PoseFilterdateResponse>

    @GET("/pose/filterpopular")
    fun posefilterpopular(): Call<RetrofitClient.PoseFilterpopular>

    @GET("/pose/delete/:id")
    fun posedeletid(): Call<RetrofitClient.PoseDeleteid>

    //마이페이지
    @GET("/app/myPage")
    fun mypage(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypage>

    //마이페이지 내가올린 강의
    @GET("/app/myPage/myClass")
    fun mypageclass(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypageclass>

    //마이페이지 내가올린 포즈
    @GET("/app/mypage/poseDrawer")
    fun mypagepose(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypagepose>

    //마이페이지 내가 올린 컨텐츠 4개씩
    @GET("/app/mypage/myContent")
    fun mypagecontents(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypagecontents>

    //마이페이지 내가 올린 10초 사진 전부
    @GET("/app/mypage/myContent/10sPhoto")
    fun mypagephoto(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypagephoto>

    //마이페이지 내가 올린 이사잘 전부
    @GET("/app/mypage/myContent/wdyt")
    fun mypagewdyt(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypagewdyt>

    //게시글 보관함
    @GET("/app/myPage/store/wdyt")
    fun mypagestorewdyt(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypagewdyt>

    //10초사진 보관함
    @GET("/app/myPage/store/10sPhoto")
    fun mypagestorephoto(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypagephoto>

    //강의 보관함
    @GET("/app/myPage/store/class")
    fun mypagestoreclass(
        @Header("x-access-token") token: String
    ) : Call<RetrofitClient.Responsemypageclass>

    @GET("/pose/getAge")
    fun posegetage(): Call<RetrofitClient.PoseGetageResponse>

    //이사잘 보관함에 넣기
    @GET("/app/wdyt/store/{id}")
    fun boxinpost(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.Responseusually>
  
    //이사잘 삭제
    @GET("/app/wdyt/delete/{id}")
    fun deletepost(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.Responseusually>

    //이사잘 보관함 꺼내기
    @GET("/app/wdyt/takeOut/{id}")
    fun boxoutpost(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.Responseusually>

    //클라스 삭제
    @GET("/app/class/deleteClass/{id}")
    fun deleteclass(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.Responseusually>

    //클래스 보관함에 넣기
    @GET("/app/class/takeOut/{id}")
    fun boxinclass(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.Responseusually>

    //클라스 꺼내기
    @GET("/app/class/takeOut/{id}")
    fun boxoutclass(
        @Header("x-access-token") token: String,
        @Path("id") id: String
    ) : Call<RetrofitClient.Responseusually>

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