package com.nicole.petnanny.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun stampToTime(time: Long) : String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        return simpleDateFormat.format(Date(time))
    }
}