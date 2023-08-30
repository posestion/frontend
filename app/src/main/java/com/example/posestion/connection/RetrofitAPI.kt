package com.example.posestion.connection

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
        @Part("introduction") introduction: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<RetrofitClient.Responseusually>

    //로그인
    @POST("/app/login")
    fun login(@Body request: RetrofitClient.Requestlogin): Call<RetrofitClient.Responselogin>

    //아이디 찾기
    @POST("/app/users/findId")
    fun findid(@Body request: RetrofitClient.Requestfindid): Call<RetrofitClient.Responsefindid>

    //비밀번호 초기화
    @PATCH("/app/users/pwReset")
    fun resetpw(@Body request: RetrofitClient.Requestpwreset): Call<RetrofitClient.Responseusually>

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

    @GET("/pose/hotSearch")
    fun posesearchhot( @Query("hot") hot: String?=null): Call<RetrofitClient.PoseSearchHotResponse>

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

    //회원정보 수정
    @Multipart
    @PATCH("/app/userchange")
    fun changeuser(
        @Header("x-access-token") token: String,
        @Part image: MultipartBody.Part,
        @Part("nickname") nickname: RequestBody,
        @Part("password") password: RequestBody?,
        @Part("birth") birth: RequestBody,
        @Part("phone_num") phoneNumber: RequestBody,
        @Part("introduction") introduction: RequestBody
    ): Call<RetrofitClient.Responseusually>

    //문의하기
    @Multipart
    @POST("/app/cs/inquiry")
    fun ask(
        @Header("x-access-token") token: String,
        @Part("title") marketingAgreement: RequestBody,
        @Part("content") userId: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<RetrofitClient.Responseusually>

    @GET("/app/cs/inquiry")
    fun myask(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.ResponsemyAsk>

    @GET("/board/getAllTensPhoto")
    fun alltensphoto(@Header("x-access-token") token: String,): Call<RetrofitClient.AllTensPhoto>

    @GET("/board/getPhotowell")
    fun boardphotowell(@Header("x-access-token") token: String,): Call<RetrofitClient.boardPhotowell>

    @GET("/board/getClass")
    fun boardclass(@Header("x-access-token") token: String,): Call<RetrofitClient.boardClass>

    @GET("/board/getHotClass")
    fun boardhotclass(@Header("x-access-token") token: String,): Call<RetrofitClient.boardHotClass>

    @GET("/board/getDibs")
    fun boarddibsclass(@Header("x-access-token") token: String,): Call<RetrofitClient.boardDibsClass>

    @POST("/app/wdyt/upload")
    fun wdytupload(
        @Header("x-access-token") token: RetrofitClient.requestclasscreation
    ): Call<RetrofitClient.responseboardcreation>

    @GET("/app/wdyt/detailPage/:id")
    fun wdytdetailpage(
        @Header("x-access-token") token: String,
        @Part("id") id: Int
    ): Call<RetrofitClient.boarddetailpage>

    @GET("/app/wdyt/like/:id")
    fun wdytlike(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,
    ): Call<RetrofitClient.wdytlike>
    @GET("/app/wdyt/cancelLike/:id")
    fun wdytcancellike(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,
    ): Call<RetrofitClient.wdytcancellike>

    @GET("/app/wdyt")
    fun wdyt(@Header("x-access-token") token: String,): Call<RetrofitClient.wdyt>
    @GET("/app/wdyt/delete/:id")
    fun wdytdelete(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,
    ): Call<RetrofitClient.wdytdelete>

    @GET("/app/wdyt/store/:id")
    fun wdyttake(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,
    ): Call<RetrofitClient.wdyttake>
    @GET("/app/wdyt/takeOut/:id")
    fun wdyttakeout(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,
    ): Call<RetrofitClient.wdyttakeout>

    @POST("/app/class/postClass")
    fun classpostclass(
        @Header("x-access-token") token: RetrofitClient.requestclasscreation
    ): Call<RetrofitClient.responseclasscreation>

    @GET("/app/class/detailPage/:id")
    fun classdetailpage(
        @Header("x-access-token") token: String,): Call<RetrofitClient.classdetailpage>

    @GET("/app/Class/dibs/:id")
    fun classdibs(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.classdibs>
    @GET("/app/class/cancelDibs/:id")
    fun classcanceldibs(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.classcanceldibs>

    @POST("/app/class/postReview/:id")
    fun classpostreview(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,
        @Part("text") text: String,
        @Part("score") score: Int
    ): Call<RetrofitClient.classreviewcreation>
    @GET("/app/class/deleteReview/:id")
    fun classdeletereview(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.classdeletereview>

    @GET("/app/class/hotClass")
    fun classhotclass(@Header("x-access-token") token: String,): Call<RetrofitClient.classhotclass>
    @GET("/app/class/drawer")
    fun classdrawer(@Header("x-access-token") token: String,): Call<RetrofitClient.classdrawer>
    @GET("/app/class/drawer/myClass")
    fun classdrawermyclass(@Header("x-access-token") token: String,): Call<RetrofitClient.classmyclass>
    @GET("/app/class/drawer/myDibs")
    fun classdrawermydibs(@Header("x-access-token") token: String,): Call<RetrofitClient.classmydibs>

    @GET("/app/class/deleteClass/:id")
    fun classdeleteclass(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.deleteclass>
    @GET("/app/class/register/:id")
    fun classregister(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.classregister>
    @GET("/app/class/store/:id")
    fun classtake(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.classtake>
    @GET("/app/class/takeOut/:id")
    fun classtakeout(
        @Header("x-access-token") token: String,
        @Part("id") id: Int,): Call<RetrofitClient.classtakeout>

    //광고
    @GET("/board/getAd")
    fun getad(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.ResponseHomeAd>

    //홈 화면 HOT강의
    @GET("/board/getHotClass")
    fun homehotclass(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.Responsehomehotclass>

    //홈 화면 인기 있는 포즈
    @GET("/pose/filterpopular")
    fun homepose(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.Responsehomepose>

    //홈 화면 수강중인 강의
    @GET("/app/class/drawer/myClass")
    fun homemyclass(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.Responsemypageclass>

    //홈 화면 찜한 강의
    @GET("/app/class/drawer/myDibs")
    fun homestarclass(
        @Header("x-access-token") token: String,
    ) : Call<RetrofitClient.Responsemypageclass>

}