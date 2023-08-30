package com.example.posestion

import android.os.Parcel
import android.os.Parcelable

data class PoseFilterdate(
    val id: Int,
    val date: String,
    val view: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val poseImage: String,
    val poseid: Int,
    val tagnames: List<String>,
    val favcount: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeInt(view)
        parcel.writeInt(userId)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(poseImage)
        parcel.writeInt(poseid)
        parcel.writeStringList(tagnames)
        parcel.writeInt(favcount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PoseFilterdate> {
        override fun createFromParcel(parcel: Parcel): PoseFilterdate {
            return PoseFilterdate(parcel)
        }

        override fun newArray(size: Int): Array<PoseFilterdate?> {
            return arrayOfNulls(size)
        }
    }
}

