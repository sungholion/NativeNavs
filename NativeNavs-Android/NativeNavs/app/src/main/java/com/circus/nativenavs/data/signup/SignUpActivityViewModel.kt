package com.circus.nativenavs.data.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpActivityViewModel : ViewModel() {

    private val _signUpDTO = MutableLiveData(
        SignUpDTO(
            "",
            "",
            false,
            "",
            emptyList(),
            "",
            "",
            "",
            "",
            false
        )
    )
    val signUpDTO: LiveData<SignUpDTO> = _signUpDTO

    fun updateEmail(email: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(email = email)
    }

    fun updatePassword(password: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(password = password)
    }

    fun updateNickname(nickname: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(nickname = nickname)
    }

    fun updateIsNav(isNav: Boolean) {
        _signUpDTO.value = _signUpDTO.value?.copy(isNav = isNav)
    }

    fun updateName(name: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(name = name)
    }

    fun updatePhone(phone: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(phone = phone)
    }

    fun updateIsKorean(isKorean: Boolean) {
        _signUpDTO.value = _signUpDTO.value?.copy(isKorean = isKorean)
    }

    fun updateBirth(birth: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(birth = birth)
    }

    fun updateNation(nation: String) {
        _signUpDTO.value = _signUpDTO.value?.copy(nation = nation)
    }

    fun updateLanguage(language: List<String>){
        _signUpDTO.value = _signUpDTO.value?.copy(language = language)
    }

    @Override
    override fun toString(): String {
        return "이름 : " + _signUpDTO.value?.name + " 전화번호 : " + _signUpDTO.value?.phone + " 언어 : " + _signUpDTO.value?.language
    }

}