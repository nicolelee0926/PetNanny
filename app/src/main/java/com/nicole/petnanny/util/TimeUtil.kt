package com.nicole.petnanny.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
//
//    fun stampToTime(time: Long) : String {
//        val simpleDateFormat = SimpleDateFormat("HH:mm")
//        return simpleDateFormat.format(Date(time))
//    }

    fun Long.toDisplayFormat(): String {
        return android.icu.text.SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.TAIWAN).format(this)
    }
}