package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pet(
    var petVariety : String? = "",
    var chipNumber : String? = "",
    var gender : String? = "",
    var petAge : String? = "",
    var petFigure : String? = "",
    var petID : String? = "",
    var petIntroduction : String? = "",
    var petLigation : String? = "",
    var petName : String? = "",
    var petPhoto : String? = "",
    var petType : String? = "",
    var petVaccine : String? = "",
    var userEmail: String?= "",
    var createTime: Long = System.currentTimeMillis()
) : Parcelable