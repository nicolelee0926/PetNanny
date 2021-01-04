package com.nicole.petnanny.util

import java.text.SimpleDateFormat
import java.util.*


fun Long.toDisplayFormat(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("Asia/Taipei")
//    formatter.timeZone = TimeZone.getTimeZone("GMT :00:00")
    return formatter.format(this)
}

