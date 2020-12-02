package com.nicole.petnanny.data

data class Order(
    var Address : String = "",
    var Note : String = "",
    var createTime : Int = 0,
    var nannyID :String = "",
    var orderEndTime : Int = 0,
    var orderID : String = "",
    var orderStartTime : Int = 0,
    var petID : String = "",
    var subPrice : Int = 0,
    var orderStatus : String = ""
)