package com.nicole.petnanny.model


data class User(
    var selfIntroduction : String = "",
    var photo : String = "",
    var userName : String = "",
    var verification : Boolean = false,
    var nannyID : String = ""
)