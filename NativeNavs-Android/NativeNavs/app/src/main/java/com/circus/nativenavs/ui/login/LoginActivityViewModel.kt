package com.circus.nativenavs.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.data.LoginDTO
import com.circus.nativenavs.data.service.UserService
import com.circus.nativenavs.util.SharedPref
import kotlinx.coroutines.launch
import retrofit2.create

class LoginActivityViewModel : ViewModel() {

    private val _loginDto = MutableLiveData<LoginDTO>()
    val loginDto: LiveData<LoginDTO> get() = _loginDto

    private val _loginStatusCode = MutableLiveData<Int>()
    val loginStatusCode: LiveData<Int> get() = _loginStatusCode

    private val retrofit = ApplicationClass.retrofit.create(UserService::class.java)

    fun Login(login: LoginDTO) {
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
//                SharedPref.userId = loginResponse?.userId
//                SharedPref.userEmail = loginResponse?.email
//                SharedPref.isNav = loginResponse?.isNav

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