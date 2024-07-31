package com.circus.nativenavs.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.circus.nativenavs.data.SignUpDTO

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

    private val _nicknameCheck = MutableLiveData<Boolean>(false)
    val nicknameCheck : LiveData<Boolean> = _nicknameCheck

    fun updateNicknameCheck(isChecked : Boolean){
        _nicknameCheck.value = isChecked
    }

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
        return "이메일 : " + _signUpDTO.value?.email +
                "\n 비밀번호 : " + _signUpDTO.value?.password +
                "\n 유저타입 : " + _signUpDTO.value?.isNav +
                "\n 닉네임 : " + _signUpDTO.value?.nickname +
                "\n 구사가능언어 : " + _signUpDTO.value?.language +
                "\n 이름 : " + _signUpDTO.value?.name +
                "\n 휴대폰 : " + _signUpDTO.value?.phone +
                "\n 국가 : " + _signUpDTO.value?.nation +
                "\n 생년월일 : " + _signUpDTO.value?.birth +
                "\n 앱 설정 언어 : " + _signUpDTO.value?.isKorean
    }


}