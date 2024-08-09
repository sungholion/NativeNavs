package com.circus.nativenavs.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.data.CategoryDto
import com.circus.nativenavs.data.LanguageDto
import com.circus.nativenavs.data.LanguageListDto
import com.circus.nativenavs.data.LanguageServerDto
import com.circus.nativenavs.data.ProfileUserDto
import com.circus.nativenavs.data.SignUpDto
import com.circus.nativenavs.data.service.UserService
import com.circus.nativenavs.util.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayInputStream
import java.io.InputStream


class HomeActivityViewModel : ViewModel() {

    private val _profileUser = MutableLiveData<ProfileUserDto>()
    val profileUser: LiveData<ProfileUserDto> get() = _profileUser


    private val _userDto = MutableLiveData<ProfileUserDto>()
    val userDto: LiveData<ProfileUserDto> get() = _userDto

    fun updateUserNickName(nick: String) {
        _userDto.value?.nickname = nick
    }

    fun updateUserPhone(phone: String) {
        _userDto.value?.phone = phone
    }

    private val userRetrofit = ApplicationClass.retrofit.create(UserService::class.java)

    fun getUser(userId: Int) {
        viewModelScope.launch {
            _userDto.value = userRetrofit.searchUser(userId)
            Log.d("Login User", "Login User: ${_userDto.value}")
        }
    }

    fun getProfileUser(userId: Int) {
        viewModelScope.launch {
            _profileUser.value = userRetrofit.searchUser(userId)
            Log.d("Login User", "getProfileUser: ${_profileUser.value}")
        }
    }

    private val _updateStatus = MutableLiveData<Int>(-1)
    val updateStatus: LiveData<Int> get() = _updateStatus

    fun updateStatusCode(i: Int) {
        _updateStatus.value = i
    }

    private val _profileModifyUser = MutableLiveData<SignUpDto>()
    val profileUserDto: LiveData<SignUpDto> get() = _profileModifyUser

    fun createEmptyImagePart(name: String): MultipartBody.Part {
        // 빈 바이트 배열 생성
        val emptyData = ByteArray(0)

        // RequestBody 생성
        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), emptyData)

        // MultipartBody.Part 생성
        return MultipartBody.Part.createFormData(name, "empty_image.png", requestBody)
    }

    fun updateUser(image: MultipartBody.Part?) {
        viewModelScope.launch {
            val userJson = Gson().toJson(_profileModifyUser.value)
            val userRequestBody = userJson.toRequestBody("application/json".toMediaTypeOrNull())
            val requestBody = MultipartBody.Part.createFormData("user", null, userRequestBody)
            // image가 null인 경우 빈 이미지 파일 생성
            val imagePart = image ?: createEmptyImagePart("profileImage")

            Log.d("notaaaaaaa", "updateUser: ${image}")

            Log.d("update", "updateUser: ${image}")
            Log.d("update", "updateUser: ${_profileModifyUser.value}")
            val response = userRetrofit.updateUser(requestBody, imagePart)
            _updateStatus.value = response?.code()
        }
    }

    fun updateProfileModifyUser(dto: SignUpDto) {
        _profileModifyUser.value = dto
    }


    private val _dupliState = MutableLiveData<Pair<Int, String>>()
    val dupliState: LiveData<Pair<Int, String>> get() = _dupliState

    private val _nicknameCheck = MutableLiveData<Boolean>(false)
    val nicknameCheck: LiveData<Boolean> = _nicknameCheck

    fun isDupli(nickname: String) {
        viewModelScope.launch {
            val response = userRetrofit.isDupliNick(nickname)
            _dupliState.postValue(Pair(response.code(), response.body().toString()))
        }
    }

    fun updateNickNameCheck(isCheck: Boolean) {
        _nicknameCheck.value = isCheck
    }

    private val _languageList = MutableLiveData(LanguageListDto(emptyList()))
    val languageList: LiveData<LanguageListDto> = _languageList

    private val _languageServerList = MutableLiveData(LanguageServerDto())
    val languageServerList: LiveData<LanguageServerDto> get() = _languageServerList

    private val _languageCheckList = MutableLiveData<List<LanguageDto>>()
    val languageCheckList: LiveData<List<LanguageDto>> get() = _languageCheckList

    private val _checkCount = MutableLiveData<Int>(0)
    val checkCount: LiveData<Int> get() = _checkCount

    fun updateCheckList(language: String, isChecked: Boolean) {
        var count = 0
        _languageCheckList.value?.onEach {
            if (it.language == language) {
                it.isChecked = isChecked
            }
            if (isChecked) count++
        }
        _checkCount.value = count
    }

    fun updateLanguageList() {
        viewModelScope.launch {
            _languageServerList.value = userRetrofit.getLanguageList()
            _languageCheckList.value =
                languageServerList.value?.map { LanguageDto(it.name, isChecked = false) }
        }
    }

    fun updateLanguage(language: List<String>) {
        _languageList.value = LanguageListDto(language)
    }

    private val _withdrawalStatus = MutableLiveData<Int>()
    val withdrawalStatus: LiveData<Int> get() = _withdrawalStatus

    fun withdrawal() {
        viewModelScope.launch {
            val response = userRetrofit.deleteUser()
            _withdrawalStatus.value = response.code()
        }
    }


    // search
    private val _searchTravel = MutableLiveData<String>("")
    val searchTravel: LiveData<String> get() = _searchTravel

    private val _searchDate = MutableLiveData<String>()
    val searchDate: LiveData<String> get() = _searchDate

    private val _searchTheme = MutableLiveData<List<Int>>()
    val searchTheme: LiveData<List<Int>> get() = _searchTheme

    private val _categoryCheckList = MutableLiveData<List<CategoryDto>>()
    val categoryCheckList: LiveData<List<CategoryDto>> get() = _categoryCheckList

    fun updateCategoryList() {
        viewModelScope.launch {
            _categoryCheckList.value = userRetrofit.getCategory()
            Log.d("category", "updateCategoryList: ${_categoryCheckList.value} ")
        }
    }

    fun toggleCategory(id: Int) {
        var updateList = _categoryCheckList.value
        updateList?.apply {
            map { if (it.id == id) it.isChecked = !it.isChecked }
        }
        Log.d("aa", "toggleCategory: ${updateList}")
        updateList?.let { _categoryCheckList.value = it }
    }

    fun resetCheck() {
        Log.d("ResetCheck", "Updated list: ${_categoryCheckList.value}")
        val updatedList = _categoryCheckList.value?.map { item ->
            item.copy(isChecked = false)
        }
        updatedList?.let {
            _categoryCheckList.value = it.toList()
        }
        Log.d("ResetCheck", "Updated list: ${_categoryCheckList.value}")
    }

    fun updateCategory() {
        _searchTheme.value = emptyList()
    }

    fun updateSearchTravel(travel: String) {
        _searchTravel.value = travel
    }

    fun updateSearchDate(travel: String) {
        _searchDate.value = travel
    }

    fun updateSearchTheme() {
        var list = mutableListOf<Int>()
        _categoryCheckList.value?.forEach {
            if (it.isChecked) list.add(it.id)
        }
        _searchTheme.value = list
    }

    //notification
    private val _notiFlag = MutableLiveData<Int>(-1)
    val notiFlag: LiveData<Int> get() = _notiFlag

    private val _notiRoomId = MutableLiveData<Int>(-1)
    val notiRoomId: LiveData<Int> get() = _notiRoomId

    private val _notiReservationId = MutableLiveData<Int>(-1)
    val notiReservationId: LiveData<Int> get() = _notiReservationId

    private val _notiTourId = MutableLiveData<Int>(-1)
    val notiTourId: LiveData<Int> get() = _notiTourId

    fun setNotiFlag(flag: Int) {
        _notiFlag.value = flag
    }

    fun setNotiRoomId(roomId: Int) {
        _notiRoomId.value = roomId
    }

    fun setNotiReservationId(reservationId: Int) {
        _notiReservationId.value = reservationId
    }

    fun setNotiTourId(tourId: Int) {
        _notiTourId.value = tourId
    }

    fun postFcmToken() {
        viewModelScope.launch {
            Log.d("fcm", "postFcmToken: ${SharedPref.fcmToken!!}")
            userRetrofit.postFcmToken(SharedPref.userId!!, SharedPref.fcmToken!!)
        }
    }
}