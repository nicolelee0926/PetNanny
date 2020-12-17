package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var address : String? = "",
    var note : String? = "",
    var orderEndTime : String? = "",
    var orderID : String? = "",
    var orderStartTime : String? = "",
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
//    nannyEmail: 用來存po服務的保姆的Email (用來query我的客戶訂單用)
    var nannyEmail:String? = "",
    var demandDay: String? = "",
    var totalPrice: String? = "",
    var lastMessage: Message?= null,
    var createTime: Long = System.currentTimeMillis()
) : Parcelable

