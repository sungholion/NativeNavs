package com.circus.nativenavs.util

import android.content.SharedPreferences

object SharedPref {
    var sharedPrefs: SharedPreferences? = null

    var userId: Int?
        get() = sharedPrefs?.getInt(USER_ID, 0) ?: 0
        set(value) {
            if (value != null) {
                sharedPrefs?.edit()?.putInt(USER_ID, value)?.apply()
            }
        }
    var userEmail: String?
        get() = sharedPrefs?.getString(EMAIL, null)
        set(value) {
            sharedPrefs?.edit()?.putString(EMAIL, value)?.apply()
        }
    var isNav: Boolean?
        get() = sharedPrefs?.getBoolean(IS_NAV, false)
        set(value) {
            if (value != null) {
                sharedPrefs?.edit()?.putBoolean(IS_NAV, value)?.apply()
            }
        }
    var accessToken: String?
        get() = sharedPrefs?.getString(ACCESSTOKEN, null)
        set(value) {
            sharedPrefs?.edit()?.putString(ACCESSTOKEN, value)?.apply()
        }

    var refreshToken: String?
        get() = sharedPrefs?.getString(REFRESHTOKEN, null)
        set(value) {
            sharedPrefs?.edit()?.putString(REFRESHTOKEN, value)?.apply()
        }


}