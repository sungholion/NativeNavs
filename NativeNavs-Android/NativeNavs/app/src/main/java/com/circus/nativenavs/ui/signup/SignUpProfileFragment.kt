package com.circus.nativenavs.ui.signup

import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpProfileBinding
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

class SignUpProfileFragment : BaseFragment<FragmentSignUpProfileBinding>(
    FragmentSignUpProfileBinding::bind,
    R.layout.fragment_sign_up_profile
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.signupTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.signupRegisterBtn.setOnClickListener {
            navigate(R.id.action_signUpProfileFragment_to_signUpCompleteFragment)
        }

        binding.signupLanguageCl.setOnClickListener {
            navigate(R.id.action_signUpProfileFragment_to_signUpLanguageFragment)
        }

        binding.signupTermsTv.setOnClickListener {
            navigate(R.id.action_signUpProfileFragment_to_signUpTosFragment)
        }
    }

}