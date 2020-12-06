package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var address : String? = "",
    var note : String? = "",
    var nannyID :String? = "",
    var orderEndTime : Long? = -1,
    var orderID : String? = "",
    var orderStartTime : Long? = -1,
    var petID : String = "",
    var subPrice : String = "",
    var userEmail: String? = "",
    var userProposeStatus: Boolean? = null,
    var nannyAcceptStatus: Boolean? = null,
    var userCheckoutStatus: Boolean? = null,
    var nannyCompletedStatus: Boolean? = null,
    var userCheckedStatus: Boolean? = null,
    var nannyRejectStatus: Boolean? = null,
) : Parcelable