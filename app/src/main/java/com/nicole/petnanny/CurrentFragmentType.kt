package com.nicole.petnanny

enum class CurrentFragmentType(val value: String) {
    HOME("寵物保姆"),
    CHAT("聊天室"),
    ORDER("訂單"),
    PROFILE("個人"),
    PROFILE_USER_EDIT("編輯個人資料"),
    PROFILE_NANNY_CENTER("保姆中心"),
    PROFILE_ADD_PET("新增寵物資料"),
    PROFILE_ADD_SERVICE("新增服務資料"),
    HOME_SEARCH_NANNY(""),
    HOME_NANNY_DETAIL(""),
    PROFILE_NANNY_CENTER_EXAMINE("審查資料"),
    PROFILE_NANNY_CENTER_LICENSE("專業證照"),
    LOGIN("")
}