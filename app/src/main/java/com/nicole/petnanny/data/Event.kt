package com.nicole.petnanny.data

data class Event (
    var eventID : String = "",
    var endTime : Int = 0,
    var eventRating : EventRating,
    var message : Message,
    var nannyID : String = "",
    var petID : String = "",
    var startTime : Int = 0
)

data class EventRating(
    var star : Int = 0
)

data class Message(
    var content : String = "",
    var isRead : Boolean = false,
    var sendID : String = "",
    var time : Int = 0
)