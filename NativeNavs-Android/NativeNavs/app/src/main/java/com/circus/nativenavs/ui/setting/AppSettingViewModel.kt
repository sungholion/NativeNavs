package com.circus.nativenavs.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.circus.nativenavs.util.SharedPref

class AppSettingViewModel : ViewModel() {

    private val _localeChanged = MutableLiveData<String>("ko")
    val localeChanged: LiveData<String> get() = _localeChanged

    // 초기 로케일 설정
    init {
        if(_localeChanged.value != SharedPref.language) {
            println("${_localeChanged.value}  ${SharedPref.language}")
            _localeChanged.value = SharedPref.language
        }
        println("${_localeChanged.value}  ${SharedPref.language}")
    }

    fun updateLocaleChange(locale : String) {
        _localeChanged.value = locale
    }

}