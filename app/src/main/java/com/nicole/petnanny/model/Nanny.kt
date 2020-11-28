package com.nicole.petnanny.model

data class Nanny(
    var serviceName : String = "",
    var servicePhoto : String = "",
    var serviceType : String = "",
    var acceptPetFigure : String = "",
    var acceptPetType : String = "",
    var license : String = "",
    var nannyID : String = "",
    var nannyIntroduction : String = "",
    var nannyName : String = "",
    var nannyPhoto : String = "",
    var price : Int = 0,
    var serviceArea : String = ""
)