package com.example.posestion

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class listpage(
    @SerializedName("title") val title: String,
    @SerializedName("page_ex") val page_ex: String,
    @SerializedName("heart") var heart: Boolean,
    @SerializedName("heart_num") var heart_num: Int,
    @SerializedName("comment_num") var comment_num: Int,
) :Parcelable