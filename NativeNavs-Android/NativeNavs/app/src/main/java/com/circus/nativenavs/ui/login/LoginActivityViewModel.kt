package com.circus.nativenavs.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.data.LoginDto
import com.circus.nativenavs.data.service.UserService
import com.circus.nativenavs.util.SharedPref
import kotlinx.coroutines.launch

class LoginActivityViewModel : ViewModel() {

    private val _loginDto = MutableLiveData<LoginDto>()
    val loginDto: LiveData<LoginDto> get() = _loginDto

    private val _loginStatusCode = MutableLiveData<Int>()
    val loginStatusCode: LiveData<Int> get() = _loginStatusCode

    private val retrofit = ApplicationClass.retrofit.create(UserService::class.java)

    private val _autoLogin = MutableLiveData<Int>()
    val autoLogin: LiveData<Int> get() = _autoLogin

    fun getAccessToken(){
        viewModelScope.launch {
            val response = retrofit.refresh(mapOf("refreshToken" to SharedPref.refreshToken!!))
            SharedPref.accessToken = response.body()?.accessToken

            _autoLogin.postValue(response.code())
        }
    }

    fun Login(login: LoginDto) {
        viewModelScope.launch {

            val response = retrofit.Login(login)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                println("로그인 성공 메시지: ${loginResponse?.message}")
                println("액세스 토큰: ${loginResponse?.accessToken}")
                println("리프레시 토큰: ${loginResponse?.refreshToken}")
                println("바디 ${loginResponse}")

                SharedPref.accessToken = loginResponse?.accessToken
                SharedPref.refreshToken = loginResponse?.refreshToken
                SharedPref.userId = loginResponse?.id
                SharedPref.isNav = loginResponse?.isNav

                println("${SharedPref.accessToken.toString()}")
                ApplicationClass.setAuthToken(SharedPref.accessToken.toString());
            } else {
                println("로그인 실패 메시지: ${response.errorBody()?.string()}")
            }

            // 상태 코드 업데이트
            _loginStatusCode.postValue(response.code())
        }
    }
}