package com.circus.nativenavs.ui.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.LanguageDto
import com.circus.nativenavs.data.LanguageListDto
import com.circus.nativenavs.data.SignUpDto
import com.circus.nativenavs.databinding.FragmentProfileModifyBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.isPasswordValid
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ProfileModifylFragment : BaseFragment<FragmentProfileModifyBinding>(
    FragmentProfileModifyBinding::bind,
    R.layout.fragment_profile_modify
) {

    private lateinit var homeActivity: HomeActivity
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()
    private var clicked: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        initViewModelEvent()


        binding.profileModifyNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeActivityViewModel.updateNickNameCheck(false)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun initViewModelEvent() {
        homeActivityViewModel.apply {
            updateStatus.observe(viewLifecycleOwner) { statusCode ->
                clicked = false
                if (statusCode != -1) {
                    when (statusCode) {
                        200 -> {
                            showToast("업데이트 성공")
                            updateUserNickName(binding.profileModifyNicknameEt.text.toString())
                            updateUserPhone(binding.profileModifyPhoneEt.text.toString())
                            updateStatusCode(-1)
                            getUser(SharedPref.userId!!)
                            getProfileUser(SharedPref.userId!!)
                            popBackStack()
                        }

                        else -> {
                            showToast("업데이트 실패")
                        }
                    }
                }

            }
            dupliState.observe(viewLifecycleOwner) { status ->
                when (status.first) {
                    200 -> {
                        homeActivityViewModel.updateNickNameCheck(true)
                        binding.profileModifyNicknameHelpTv.visibility = INVISIBLE
                        binding.profileModifyDupliOk.visibility = VISIBLE
                        binding.profileModifyBad.visibility = INVISIBLE
                    }

                    else -> {
                        if (binding.profileModifyNicknameEt.text.toString() ==
                            homeActivityViewModel.userDto.value?.nickname
                        )
                            homeActivityViewModel.updateNickNameCheck(true)
                        else {
                            homeActivityViewModel.updateNickNameCheck(false)
                            binding.profileModifyNicknameHelpTv.visibility = INVISIBLE
                            binding.profileModifyDupliOk.visibility = INVISIBLE
                            binding.profileModifyBad.visibility = VISIBLE
                        }
                    }
                }
            }
            languageList.observe(viewLifecycleOwner) { languageList ->
                if (languageList != LanguageListDto(emptyList())) binding.profileModifySelectedLanguageTv.text =
                    languageList.language.joinToString(", ")
            }

            body.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.profileModifyUserImgIv.setImageURI(imageUri.value)
                }
            }

        }
    }

    fun initView() {

        binding.profileModifyTitle.titleText = getString(R.string.app_bar_profile_modify)

        homeActivityViewModel.updateNickNameCheck(true)
        binding.apply {
            homeActivityViewModel.let { vm ->
                vm.profileUser.value?.let {

                    if (homeActivityViewModel.body.value != null) {
                        binding.profileModifyUserImgIv.setImageURI(vm.imageUri.value)
                    } else {
                        Glide.with(requireContext())
                            .load(it.image)
                            .placeholder(R.drawable.logo_nativenavs)
                            .error(R.drawable.logo_nativenavs)
                            .fallback(R.drawable.logo_nativenavs)
                            .into(binding.profileModifyUserImgIv)
                    }

                    profileModifyNameEt.setText(it.name)
                    profileModifyNicknameEt.setText(it.nickname)
                    profileModifyNationalityEt.setText(it.nation)
                    profileModifyBirthEt.setText(it.birth.substring(0, 10))
                    profileModifySelectedLanguageTv.setText(it.userLanguage)
                    profileModifyPhoneEt.setText(it.phone)
                }
            }
        }


    }

    // 이미지 선택 인텐트 시작
    private fun openImagePicker() {
        getImageLauncher.launch("image/*")
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val contentResolver = context.contentResolver
        val file =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.jpg")

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
        Log.d(
            "FileConversion",
            "File Path: ${file.absolutePath}, File Size: ${file.length()} bytes"
        )
        return file
    }

    private fun compressImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        val compressedFile = File(file.parent, "compressed_${file.name}")
        FileOutputStream(compressedFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // 80% 압축 품질
        }
        return compressedFile
    }

    // 선택한 이미지 처리
    private fun handleImage(imageUri: Uri) {
        var file = uriToFile(requireContext(), imageUri)

        val maxSize = 10 * 1024 * 1024 // 10MB
        if (file.length() > maxSize) {
            file = compressImage(file)

            // 압축 후에도 파일 크기가 허용 범위를 초과하는지 확인
            if (file.length() > maxSize) {
                showToast("File size still exceeds limit after compression")
                return
            }
        }
        Log.d("handle", "handleImage: ${file.length()}")
        // 파일을 MultipartBody.Part로 변환
        val requestFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        homeActivityViewModel.updateImageFile(
            MultipartBody.Part.createFormData(
                "profileImage",
                file.name,
                requestFile
            ), imageUri
        )

    }

    // ActivityResultLauncher 선언
    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { handleImage(it) }
        }

    fun initEvent() {
        binding.profileModifyUserImgCv.setOnClickListener {
            openImagePicker()
        }

        binding.profileModifyCompleteBtn.setOnClickListener {
            if (clicked) showToast("잠시만 기다려 주세요")
            else {
//                clicked = true
//                val password = binding.profileModifyPasswordEt.text.toString()
//                val passwordCheck = binding.profileModifyPasswordCheckEt.text.toString()
//                if (!isPasswordValid(password)) {
//                    clicked = false
//                    showToast(getString(R.string.profile_password_message))
//                } else if (passwordCheck != password) {
//                    showToast(getString(R.string.profile_password_check_message))
//                    clicked = false
//                } else
                    if (homeActivityViewModel.nicknameCheck.value == false){
                    showToast(getString(R.string.profile_nickname_check))
                    clicked = false
                }
                else {
                    homeActivityViewModel.let {
                        binding.apply {
                            it.updateProfileModifyUser(
                                SignUpDto(
                                    email = it.userDto.value!!.email,
                                    password = "",
                                    isNav = it.userDto.value!!.isNav,
                                    nickname = profileModifyNicknameEt.text.toString(),
                                    userLanguage = profileModifySelectedLanguageTv.text.toString(),
                                    name = it.userDto.value!!.name,
                                    phone = profileModifyPhoneEt.text.toString(),
                                    nation = it.userDto.value!!.nation,
                                    birth = it.userDto.value!!.birth,
                                    device = it.userDto.value!!.device,
                                    image = it.userDto.value!!.image,
                                    isKorean = it.userDto.value!!.korean
                                )
                            )
                        }
                        it.updateUser()
                    }
                }
            }
        }

        binding.profileModifyNicknameCheckBtn.setOnClickListener {
            homeActivityViewModel.isDupli(binding.profileModifyNicknameEt.text.toString())
        }

        binding.profileModifyLanguageCl.setOnClickListener {
            navigate(R.id.action_profileModifylFragment_to_profileLanguageFragment)
        }

        binding.profileModifyTitle.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }
    }

}