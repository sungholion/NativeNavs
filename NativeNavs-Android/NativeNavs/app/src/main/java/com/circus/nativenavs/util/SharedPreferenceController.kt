package com.circus.nativenavs.util

import android.content.Context

class SharedPreferenceController(context: Context) {
    private val prefs = context.getSharedPreferences("pref_name", Context.MODE_PRIVATE)

}