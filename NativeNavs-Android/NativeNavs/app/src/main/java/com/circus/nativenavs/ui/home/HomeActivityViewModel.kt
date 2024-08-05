package com.circus.nativenavs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.data.LanguageDto
import com.circus.nativenavs.data.LanguageListDto
import com.circus.nativenavs.data.LanguageServerDto
import com.circus.nativenavs.data.ProfileUserDto
import com.circus.nativenavs.data.SignUpDto
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.data.service.UserService
import kotlinx.coroutines.launch

class HomeActivityViewModel : ViewModel() {

    private val _profileUser = MutableLiveData<ProfileUserDto>()
    val profileUser : LiveData<ProfileUserDto> get() = _profileUser


    private val _userDto = MutableLiveData<ProfileUserDto>()
    val userDto : LiveData<ProfileUserDto> get() = _userDto

    private val userRetrofit = ApplicationClass.retrofit.create(UserService::class.java)

    fun getUser(userId : Int){
        viewModelScope.launch {
            _userDto.value = userRetrofit.searchUser(userId)
        }
    }

    fun getProfileUser(userId : Int){
        viewModelScope.launch {
            _profileUser.value = userRetrofit.searchUser(userId)
        }
    }

    private val _updateStatus = MutableLiveData<Int>()
    val updateStatus : LiveData<Int> get() = _updateStatus

    private val _profileModifyUser = MutableLiveData<SignUpDto>()
    val profileUserDto : LiveData<SignUpDto> get() = _profileModifyUser

    fun updateUser(){
        viewModelScope.launch {
            val response = _profileModifyUser.value?.let { userRetrofit.updateUser(it) }
            _updateStatus.value = response?.code()
        }
    }

    fun updateProfileModifyUser(dto : SignUpDto){
        _profileModifyUser.value = dto
    }


    private val _dupliState = MutableLiveData<Pair<Int,String>>()
    val dupliState: LiveData<Pair<Int,String>> get() = _dupliState

    private val _nicknameCheck = MutableLiveData<Boolean>(false)
    val nicknameCheck : LiveData<Boolean> = _nicknameCheck

    fun isDupli(nickname:String){
        viewModelScope.launch {
            val response = userRetrofit.isDupliNick(nickname)
            _dupliState.postValue(Pair(response.code(),response.body().toString()))
        }
    }

    fun updateNickNameCheck(isCheck :Boolean){
        _nicknameCheck.value = isCheck
    }

    private val _languageList = MutableLiveData(LanguageListDto(emptyList()))
    val languageList: LiveData<LanguageListDto> = _languageList

    private val _languageServerList = MutableLiveData(LanguageServerDto())
    val languageServerList : LiveData<LanguageServerDto> get() = _languageServerList

    private val _languageCheckList = MutableLiveData<List<LanguageDto>>()
    val languageCheckList : LiveData<List<LanguageDto>> get() = _languageCheckList

    private val _checkCount = MutableLiveData<Int>(0)
    val checkCount : LiveData<Int> get() = _checkCount

    fun updateCheckList(language : String, isChecked: Boolean){
        var count = 0
        _languageCheckList.value?.onEach {
            if(it.language == language) {
                it.isChecked = isChecked
            }
            if(isChecked) count++
        }
        _checkCount.value = count
    }

    fun updateLanguageList(){
        viewModelScope.launch {
            _languageServerList.value = userRetrofit.getLanguageList()
            _languageCheckList.value = languageServerList.value?.map { LanguageDto(it.name, isChecked = false) }
        }
    }
    fun updateLanguage(language : List<String>){
        _languageList.value = LanguageListDto(language)
    }

    private val _withdrawalStatus = MutableLiveData<Int>()
    val withdrawalStatus : LiveData<Int> get() = _withdrawalStatus

    fun withdrawal(){
        viewModelScope.launch {
            val response = userRetrofit.deleteUser()
            _withdrawalStatus.value = response.code()
        }
    }

}