package com.example.posestion.connection

import com.google.gson.annotations.SerializedName

class RetrofitClient {

    //로그인
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

    //아이디 찾기
    data class Requestfindid(
        @SerializedName("username")
        val username: String,
        @SerializedName("phone_num")
        val phonenum: String
    )

    data class Responsefindid(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: FindidResult
    )

    data class FindidResult(
        @SerializedName("user_id")
        val userid: String,
    )

    //비밀번호 초기화
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
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    //아이디 중복확인
    data class Responsecheckid(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<CheckidResult>
    )

    data class CheckidResult(
        @SerializedName("user_id")
        val userid: String
    )

    //닉네임 중복 확인
    data class Responsenickname(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<NicknameResult>
    )

    data class NicknameResult(
        @SerializedName("nickname")
        val nickname: String
    )

    //회원가입
    data class ResponseSignup(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    //회원 정보
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
        val nick: String,
        @SerializedName("username")
        val name: String
    )

    //내가 올린 컨텐츠
    data class ResponsemyContent(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<myContent>
    )

    data class myContent(
        @SerializedName("category")
        val category: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("like") // 0 : 내가 좋아요하지 않음 , 1 : 내가 좋아요함
        val like: Int,
        @SerializedName("Number_of_like")
        val likeNum: Int,
        @SerializedName("Number_of_reply")
        val replyNum: Int
    )

    //내가 올린 강의
    data class ResponsemyClass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<myClass>
    )

    data class myClass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val image: String,
    )

    //마이페이지
    data class Responsemypage(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<mypage>
    )

    data class mypage(
        @SerializedName("expert")
        val expert: Int,
        @SerializedName("profile_image")
        val profile: String,
        @SerializedName("nickname")
        val nick: String,
        @SerializedName("post_count")
        val post: Int,
        @SerializedName("follower")
        val follower: Int,
        @SerializedName("following")
        val following: Int,
        @SerializedName("inroduction")
        val inroduction: String?,
    )

    //이사잘 게시물 삭제
    data class ResponseDeletepost(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    //문의하기
    data class ResponseAsk(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    //내 문의내역
    data class ResponsemyAsk(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: askdata
    )

    data class askdata(
        @SerializedName("answer_incomplete")
        val answerincomplete: List<answerx>,
        @SerializedName("answer_complete")
        val answercomplete: List<answero>
    )

    data class answerx(
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String
    )

    data class answero(
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("ans_name")
        val ansname: String,
        @SerializedName("ans_content")
        val anscontent: String
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
    data class PoseSearchResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<PoseSearch>
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
        val tagname: List<String>?
    )
    data class PoseAddfavoriteResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: PoseAddfavorite
    )
    data class PoseAddfavorite(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?,
    )

    data class PoseDelfavorite(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String?
    )
    data class PoseFavoriteviewResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<PoseFavoriteview>
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
        val tagname: List<String>?
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
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("tag_name")
        val tagname: List<String>?
    )

    data class PoseFilterdateResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<PoseFilterdate>
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
        @SerializedName("pose_id")
        val poseid: Int,
        @SerializedName("tag_name")
        val tagname: String?,
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

    data class Responsemypage1(
        @SerializedName("token")
        val token: String?
    )

    data class PoseGetageResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<PoseGetage>
    )

    data class PoseGetage(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("view")
        val view: Int,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("tag_names")
        val tagnames: List<String>?
    )

    data class AllTensPhoto(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<TensPhoto>
    )
    data class TensPhoto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("pose_image")
        val poseImage: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("nickname")
        val nickname: String
    )

    data class boardPhotowell(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<photowell>
    )
    data class photowell(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val image_url: String,
        @SerializedName("like")
        val like: Int, //1->좋아요 누름, 0->좋아요 안누름
        @SerializedName("Number_of_like")
        var Number_of_like: Int,
        @SerializedName("Number_of_reply")
        val Number_of_reply: Int
    )

    data class boardClass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<getclass>
    )
    data class getclass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("date")
        val date: String
    )

    data class boardHotClass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<getHotClass>
    )
    data class getHotClass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Int
    )

    data class boardDibsClass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<getDibsClass>
    )
    data class getDibsClass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Boolean
    )

    data class requestboardcreation(
        @SerializedName("image")
        val image: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String
    )
    data class responseboardcreation(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    data class boarddetailpage(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: boarddetailpage_result
    )
    data class boarddetailpage_result(
        @SerializedName("wdytContent")
        val wdytContent: List<wdytContent>,
        @SerializedName("wdytImages")
        val wdytImages: List<wdytImages>
    )
    data class wdytContent(
        @SerializedName("id")
        val id: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("like")
        val like: Int, //1->좋아요 누름, 0->좋아요 안누름
        @SerializedName("nickname")
        val ni: String,
        @SerializedName("profile_image")
        val profileImage: String,
        @SerializedName("Number_of_like")
        val Number_of_like: Int,
        @SerializedName("Number_of_reply")
        val Number_of_reply: Int
    )
    data class wdytImages(
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("sequence")
        val sequence: Int,
    )

    data class wdytlike(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class wdytcancellike(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    data class wdyt(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<wdytlist>
    )
    data class wdytlist(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("like")
        val like: Int, //1->좋아요 누름, 0->좋아요 안누름
        @SerializedName("Number_of_like")
        var Number_of_like: Int,
        @SerializedName("Number_of_reply")
        val Number_of_reply: Int
    )
    data class wdytdelete(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class wdyttake(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class wdyttakeout(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    data class requestclasscreation(
        @SerializedName("image")
        val Image: String?,
        @SerializedName("title")
        val title: String,
        @SerializedName("content")
        val content: String
    )
    data class responseclasscreation(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )

    data class classdetailpage(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: classdetailpage_result
    )
    data class classdetailpage_result(
        @SerializedName("classdetailcontent")
        val classdetailcontent: List<classdetailcontent>,
        @SerializedName("classdetailimage")
        val classdetailimage: List<classdetailimage>,
        @SerializedName("classdetailreviews")
        val classdetailreviews: List<classdetailreviews>,
        @SerializedName("classdetaildibs")
        val classdetaildibs: List<classdetaildibs>
    )
    data class classdetailcontent(
        @SerializedName("classId")
        val classId: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("nickname")
        val ni: String,
        @SerializedName("userProfile")
        val userProfile: String,
        @SerializedName("isExpert")
        val isExpert: Int,
    )
    data class classdetailimage(
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("sequence")
        val sequence: Int
    )
    data class classdetailreviews(
        @SerializedName("nickname")
        val ni: String,
        @SerializedName("userProfile")
        val userProfile: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("text")
        val text: String,
        @SerializedName("score")
        val score: Int,
    )
    data class classdetaildibs(
        @SerializedName("dibs")
        val dibs: Boolean
    )

    data class classdibs(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classcanceldibs(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classreviewcreation(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classdeletereview(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classhotclass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<hotclass>
    )
    data class hotclass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Int //0->찜X, 1->찜O
    )
    data class classdrawer(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: classdrawer_result
    )
    data class classdrawer_result(
        @SerializedName("drawermyclass")
        val drawermyclass: List<drawermyclass>,
        @SerializedName("drawerdibs")
        val drawerdibs: List<drawerdibs>
    )
    data class drawermyclass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Int //0->찜X, 1->찜O
    )
    data class drawerdibs(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Int //0->찜X, 1->찜O
    )

    data class classmyclass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<listenmyclass>
    )
    data class listenmyclass(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Int //0->찜X, 1->찜O
    )
    data class classmydibs(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<mydibs>
    )
    data class mydibs(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("image_url")
        val Image_url: String,
        @SerializedName("hits")
        val hits: Int,
        @SerializedName("dibs")
        val dibs: Int //0->찜X, 1->찜O
    )
    data class deleteclass(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classregister(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classtake(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
    data class classtakeout(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
}