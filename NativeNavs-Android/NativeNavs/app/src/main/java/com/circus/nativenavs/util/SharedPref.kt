package com.circus.nativenavs.util

import android.content.SharedPreferences

object SharedPref {
    var sharedPrefs: SharedPreferences? = null

    var userId: Int
        get() = sharedPrefs?.getInt(USER_ID, 0) ?: 0
        set(value) {
            sharedPrefs?.edit()?.putInt(USER_ID, value)?.apply()
        }


    var language: String?
        get() = sharedPrefs?.getString(LANGUAGE, "ko") ?: "ko"
        set(value) {
            sharedPrefs?.edit()?.putString(LANGUAGE, value)?.apply()
        }

}