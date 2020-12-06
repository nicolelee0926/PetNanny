package com.nicole.petnanny.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
    var eventID : String? = "",
    var message : List<Message>?,
    var nannyID : String? = "",
    var petID : String? = "",
    var userIDN: String? = ""
): Parcelable

@Parcelize
data class Message(
    var content : String? = "",
    var isRead : Boolean? = null,
    var senderID : String? = "",
    var messageTime : Long? = -1,
    var receiverID : String?= ""
): Parcelable