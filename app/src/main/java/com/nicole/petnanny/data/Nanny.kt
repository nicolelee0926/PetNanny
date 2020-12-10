package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nanny(
    var serviceName : String? = "",
    var servicePhoto : String? = "",
    var serviceType : String? = "",
    var ID : String? = "",
    var acceptPetFigure : String? = "",
    var acceptPetType : String? = "",
    var license : String? = "",
    var nannyBirthday : String? = "",
    var nannyIDPhoto : String? = "",
    var nannyIDNumber : String? = "",
    var nannyIntroduction : String? = "",
    var nannyName : String? = "",
    var nannyPetExperience : String? = "",
    var price : String? = "",
    var serviceArea : String? = "",
    var nannyPhoto : String? = "",
    var nannyPhone: String?= "",
    var nannySelfPhoto : String? = "",
    var userEmail: String? = ""
) : Parcelable

