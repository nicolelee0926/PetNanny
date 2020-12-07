package com.nicole.petnanny.data

import android.os.Parcelable
import com.nicole.petnanny.ui.chat.ChatRoomDetailAdapter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
    var eventID : String? = "",
    var message : List<Message>?,
    var nannyID : String? = "",
    var orderID : String? = "",
    var userID: String? = ""
): Parcelable

@Parcelize
data class Message(
    var content : String? = "",
    var isRead : Boolean? = null,
    var sender : User? = null,
    var messageTime : Long? = -1,
    var receiver : User? = null
): Parcelable

@Parcelize
data class userTextList(
    var userText: List<Message>? = null
): Parcelable{
    fun toUserTextItem(): List<ChatRoomDetailAdapter.CRText>{
        val items = mutableListOf<ChatRoomDetailAdapter.CRText>()
        userText?.let {
            for (item in it){
                if (item.sender?.userEmail == "0" ){
                    items.add(ChatRoomDetailAdapter.CRText.Received(item))
                }
                else{
                    items.add(ChatRoomDetailAdapter.CRText.Send(item))
                }
            }
        }
        return items
    }
}