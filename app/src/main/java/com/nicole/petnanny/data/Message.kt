package com.nicole.petnanny.data

import android.os.Parcelable
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var content : String? = "",
    var isRead : Boolean? = null,
    var messageTime : Long? = -1,
    var senderImage: String= "",
    var senderEmail: String= "",
    var id : String = "",
    var senderName :String? = ""
): Parcelable

