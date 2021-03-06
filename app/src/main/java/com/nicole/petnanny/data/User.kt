package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var selfIntroduction : String? = "",
    var photo : String? = "",
    var userName : String? = "",
    var verification : Boolean? = null,
    var userEmail : String? ="",
    var petIdList: List<String>? = null
) : Parcelable

