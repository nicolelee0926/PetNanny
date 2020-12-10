package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var address : String? = "",
    var note : String? = "",
    var orderEndTime : Long? = -1,
    var orderID : String? = "",
    var orderStartTime : Long? = -1,
    var petID : String = "",
    var subPrice : String = "",
    var userEmail: String? = "",
    var price: String? = "",
    var userProposeStatus: Boolean? = false,
    var nannyAcceptStatus: Boolean? = false,
    var userCheckoutStatus: Boolean? = false,
    var nannyCompletedStatus: Boolean? = false,
    var userCheckedStatus: Boolean? = false,
    var nannyRejectStatus: Boolean? = false,
    var nannyServiceDetail: Nanny? = null,
    var userInfo: User? = null,
    var nannyEmail:String? = "",
    var demandDay: String? = "",
    var totalPrice: String? = "",
) : Parcelable