package com.circus.nativenavs.ui.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.SignUpDto
import com.circus.nativenavs.databinding.FragmentPasswordBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.isPasswordValid
import com.circus.nativenavs.util.popBackStack

class PasswordFragment : BaseFragment<FragmentPasswordBinding>(
    FragmentPasswordBinding::bind,
    R.layout.fragment_password
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

        initView()
        initEvent()

    }

    private fun initView(){
        binding.passwordTitle.titleText = getString(R.string.mypage_password)
    }

    private fun initEvent() {
        binding.passwordTitle.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.passwordCompleteBtn.setOnClickListener {
            clicked = true
            val password = binding.passwordEt.text.toString()
            val passwordCheck = binding.passwordCheckEt.text.toString()
            if (!isPasswordValid(password)) {
                clicked = false
                showToast(getString(R.string.profile_password_message))
            } else if (passwordCheck != password) {
                showToast(getString(R.string.profile_password_check_message))
                clicked = false
            } else {
                homeActivityViewModel.let {
                    binding.apply {
                        it.updateProfileModifyUser(
                            SignUpDto(
                                email = it.userDto.value!!.email,
                                password = passwordEt.text.toString(),
                                isNav = it.userDto.value!!.isNav,
                                nickname = it.userDto.value!!.nickname,
                                userLanguage = it.userDto.value!!.userLanguage,
                                name = it.userDto.value!!.name,
                                phone = it.userDto.value!!.phone,
                                nation = it.userDto.value!!.nation,
                                birth = it.userDto.value!!.birth,
                                device = it.userDto.value!!.device,
                                image = it.userDto.value!!.image,
                                isKorean = it.userDto.value!!.korean
                            )
                        )
                    }
                    it.updateUser()
                    popBackStack()
                }
            }
        }
    }
}