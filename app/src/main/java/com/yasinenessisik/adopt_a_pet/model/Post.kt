package com.yasinenessisik.adopt_a_pet.model

import android.os.Parcel
import android.os.Parcelable
import java.security.Timestamp
import java.text.SimpleDateFormat


class Post(
    val imageurl: String?,
    val usermail:String?,
    val useruid:String?,
    val petname:String?,
    val petspecies:String?,
    val petbreed:String?,
    val petage:String?,
    val petcity:String?,
    val petdistrict:String?,
    val petgender:String?,
    val petexplanation:String?,
    val peturgency: Long?,
    val docId:String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageurl)
        parcel.writeString(usermail)
        parcel.writeString(useruid)
        parcel.writeString(petname)
        parcel.writeString(petspecies)
        parcel.writeString(petbreed)
        parcel.writeString(petage)
        parcel.writeString(petcity)
        parcel.writeString(petdistrict)
        parcel.writeString(petgender)
        parcel.writeString(petexplanation)
        if (peturgency != null) {
            parcel.writeLong(peturgency)
        }
        parcel.writeString(docId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }



}