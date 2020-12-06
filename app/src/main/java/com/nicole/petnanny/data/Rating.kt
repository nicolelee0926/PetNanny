package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.IDN

@Parcelize
data class Rating(
    var content: String? ="",
    var createTime: Long? = -1,
    var nannyID: String?= "",
    var orderID: String?= "",
    var star: Int?= -1,
    var userID: String?= ""
) : Parcelable