package com.circus.nativenavs.ui.home

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
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
import com.circus.nativenavs.data.ProfileUserReviewDto
import com.circus.nativenavs.data.ReservationDto
import com.circus.nativenavs.data.SignUpDto
import com.circus.nativenavs.data.StampDto
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
import kotlin.math.log


class HomeActivityViewModel : ViewModel() {

    private val _profileUser = MutableLiveData<ProfileUserDto>()
    val profileUser: LiveData<ProfileUserDto> get() = _profileUser

    private val _userDto = MutableLiveData<ProfileUserDto>()
    val userDto: LiveData<ProfileUserDto> get() = _userDto

    private var _body = MutableLiveData<MultipartBody.Part?>(null)
    val body : LiveData<MultipartBody.Part?> get() = _body
    private var _imageUri = MutableLiveData<Uri>()

    val imageUri : LiveData<Uri> = _imageUri
    fun updateImageFile(image :MultipartBody.Part, uri : Uri){
        _imageUri.value = uri
        _body.value = image
    }

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
    fun updateUser() {
        viewModelScope.launch {
            val userJson = Gson().toJson(_profileModifyUser.value)
            val userRequestBody = userJson.toRequestBody("application/json".toMediaTypeOrNull())
            val requestBody = MultipartBody.Part.createFormData("user", null, userRequestBody)
            // image가 null인 경우 빈 이미지 파일 생성
            val imagePart = _body.value ?: createEmptyImagePart("profileImage")

            Log.d("notaaaaaaa", "updateUser: ${_body.value}")

            Log.d("update", "updateUser: ${_body.value}")
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

    private val _searchDate = MutableLiveData<String>("")
    val searchDate: LiveData<String> get() = _searchDate

    private val _searchTheme = MutableLiveData<List<Int>>(emptyList())
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

    private val _profileUserReview = MutableLiveData<ProfileUserReviewDto>()
    val profileUserReviewDto : LiveData<ProfileUserReviewDto> get() = _profileUserReview

    private val _reviewStatus = MutableLiveData<Int>(-1)
    val reviewStatus : LiveData<Int> get() = _reviewStatus
    fun getNavReview(id : Int){
        viewModelScope.launch {
            Log.d("review", "getNavReview: \"나는 여기\"")
            _profileUserReview.value = userRetrofit.getNavReview(id)
            _reviewStatus.value?.let { it+1 }
         }
    }
    fun getTravReview(id : Int){
        viewModelScope.launch {
            Log.d("review", "getTravReview: \"나는 여기\"")
            _profileUserReview.value = userRetrofit.getTravReview(id)
            var count = _reviewStatus.value
            if (count != null) {
                _reviewStatus.value = count+1
            }
        }
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


    private val _stamp = MutableLiveData<List<StampDto>>()
    val stamp : LiveData<List<StampDto>> get() = _stamp

    fun getStamp(usserId : Int){

        viewModelScope.launch {
            _stamp.value = userRetrofit.getStamp(usserId)
        }
    }

    private val _reservation = MutableLiveData<ReservationDto>()
    val reservation : LiveData<ReservationDto> get() = _reservation

    fun getReservation(reservationId: Int){
        viewModelScope.launch {
            _reservation.value = userRetrofit.getReservation(reservationId)
        }
    }
    private val _reservationStatus = MutableLiveData<Int>(-1)
    val reservationStatus : LiveData<Int> get() = _reservationStatus
    fun updateReservationStatus(reservationId: Int){
        viewModelScope.launch {
            val response = userRetrofit.updateReservationStatus(reservationId)
            updateReservationStatusCode(response.code())
        }
    }
    fun updateReservationStatusCode(code : Int)
    {
        _reservationStatus.value = code
    }
}