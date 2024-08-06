package com.circus.nativenavs.ui.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

class ProfileModifylFragment : BaseFragment<FragmentProfileModifyBinding>(
    FragmentProfileModifyBinding::bind,
    R.layout.fragment_profile_modify
) {

    private lateinit var homeActivity: HomeActivity

    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()

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
                if(statusCode != -1){
                    when (statusCode) {
                        200 -> {
                            showToast("업데이트 성공")
                            updateUserNickName(binding.profileModifyNicknameEt.text.toString())
                            updateUserPhone(binding.profileModifyPhoneEt.text.toString())
                            updateStatusCode(-1)
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
        }
    }

    fun initView() {

        homeActivityViewModel.updateNickNameCheck(true)
        binding.apply {
            homeActivityViewModel.profileUser.value?.let {
                profileModifyUserImgIv.setImageURI(it.image.toUri())
                profileModifyNameEt.setText(it.name)
                profileModifyNicknameEt.setText(it.nickname)
                profileModifyNationalityEt.setText(it.nation)
                profileModifyBirthEt.setText(it.birth.substring(0, 9))
                profileModifySelectedLanguageTv.setText(it.userLanguage)
                profileModifyPhoneEt.setText(it.phone)
            }
        }


    }
    // 선택한 이미지 처리
    private fun handleImage(imageUri: Uri) {
        Log.d("YourFragment", "Selected Image URI: $imageUri")
        binding.profileModifyUserImgIv.setImageURI(imageUri)
        // 필요 시 이미지 업로드 추가 처리
    }
    // ActivityResultLauncher 선언
    private val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { handleImage(it) }
    }
    private fun openImagePicker() {
        getImageLauncher.launch("image/*")
    }
    fun initEvent() {
        binding.profileModifyUserImgCv.setOnClickListener {
            openImagePicker()
        }
        binding.profileModifyCompleteBtn.setOnClickListener {

            val password = binding.profileModifyPasswordEt.text.toString()
            val passwordCheck = binding.profileModifyPasswordCheckEt.text.toString()
            if (!isPasswordValid(password)) {
                showToast(getString(R.string.profile_password_message))
            } else if (passwordCheck != password) {
                showToast(getString(R.string.profile_password_check_message))
            } else if (homeActivityViewModel.nicknameCheck.value == false) showToast(getString(R.string.profile_nickname_check))
            else {
                homeActivityViewModel.let {
                    binding.apply {
                        it.updateProfileModifyUser(
                            SignUpDto(
                                email = it.userDto.value!!.email,
                                password = profileModifyPasswordEt.text.toString(),
                                isNav = it.userDto.value!!.isNav,
                                nickname = profileModifyNicknameEt.text.toString(),
                                userLanguage = profileModifySelectedLanguageTv.text.toString(),
                                name = it.userDto.value!!.name,
                                phone = profileModifyPhoneEt.text.toString(),
                                nation = it.userDto.value!!.nation,
                                birth = it.userDto.value!!.birth,
                                image = " ",
                                device = it.userDto.value!!.device,
                                isKorean = it.userDto.value!!.korean
                            )
                        )
                    }
                    it.updateUser()
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
            findNavController().popBackStack()
        }
    }

}