package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var selfIntroduction : String? = "",
    var photo : String? = "",
    var userName : String? = "",
    var verification : Boolean? = null,
    var nannyID : String? = "",
    var userID : String? ="",
    var userEmail : String? ="",
    var petID: String?= ""
) : Parcelable

