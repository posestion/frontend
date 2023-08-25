package com.example.posestion

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class home_10photo (
    @SerializedName("photo") var photo:Int,
    @SerializedName("title") var title:String,
    @SerializedName("job") var job:String
    ) : Parcelable