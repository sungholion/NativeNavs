package com.circus.nativenavs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.data.ProfileUserDto
import com.circus.nativenavs.data.service.UserService
import kotlinx.coroutines.launch

class HomeActivityViewModel : ViewModel() {

    private val _profileUser = MutableLiveData<ProfileUserDto>()
    val profileUser : LiveData<ProfileUserDto> get() = _profileUser

    private val userRetrofit = ApplicationClass.retrofit.create(UserService::class.java)

    fun getProfileUser(userId : Int){
        viewModelScope.launch {
            _profileUser.value = userRetrofit.searchUser(userId)
        }
    }

}