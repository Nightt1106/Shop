package com.Night.shop

import android.app.Activity
import android.content.Context
import kotlinx.android.synthetic.main.activity_main.*

fun Activity.setNickName(nickname:String){
    getSharedPreferences("shop",Context.MODE_PRIVATE)
        .edit()
        .putString("NICKNAME",nickname)
        .apply()
}

fun Activity.getNickName(): String? {
    return getSharedPreferences("shop",Context.MODE_PRIVATE).getString("NICKNAME","")
}