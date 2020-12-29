package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NannyExamine(
    var nannyRealName : String? = "",
    var nannyBirthday : String? = "",
    var nannyPhone: String?= "",
    var nannyIDNumber : String? = "",
    var nannyPetExperience : String? = "",
    var userEmail: String?= "",
) : Parcelable

