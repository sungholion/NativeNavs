package com.circus.nativenavs.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleUtils {
    fun setLocale(context: Context, languageCode: String) {
        val locale = when (languageCode) {
            "ko" -> Locale.KOREAN
            "en" -> Locale.US
            else -> Locale.KOREAN
        }
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}