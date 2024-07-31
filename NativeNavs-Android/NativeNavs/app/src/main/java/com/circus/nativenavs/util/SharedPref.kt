package com.circus.nativenavs.util

import android.content.SharedPreferences

object SharedPref {
    var sharedPrefs: SharedPreferences? = null

    var userId: Int
        get() = sharedPrefs?.getInt(USER_ID, 0) ?: 0
        set(value) {
            sharedPrefs?.edit()?.putInt(USER_ID, value)?.apply()
        }

    var isNav: Boolean
        get() = sharedPrefs?.getBoolean(IS_NAV, false) ?: false
        set(value) {
            sharedPrefs?.edit()?.putBoolean(IS_NAV, value)?.apply()
        }
}