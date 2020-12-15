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

//@Parcelize
//data class MessageList(
//    var messageText: List<Message>? = null
//): Parcelable{
//    fun toMessageListItem(): List<ChatRoomDetailAdapter.Meesaage>{
//        val items = mutableListOf<ChatRoomDetailAdapter.Meesaage>()
//        messageText?.let {
//            for (item in it){
//                if (item.senderEmail == "me" ){
//                    items.add(ChatRoomDetailAdapter.Meesaage.Received(item))
//                }
//                else{
//                    items.add(ChatRoomDetailAdapter.Meesaage.Send(item))
//                }
//            }
//        }
//        return items
//    }
//}