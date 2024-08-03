package com.circus.nativenavs.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpUserTypeBinding
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

class SignUpUserTypeFragment : BaseFragment<FragmentSignUpUserTypeBinding>(
    FragmentSignUpUserTypeBinding::bind,
    R.layout.fragment_sign_up_user_type
) {

    private val signUpViewModel: SignUpActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.signupTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.signupTypeNavLl.setOnClickListener {
            signUpViewModel.updateIsNav(true)
            navigate(R.id.action_signUpUserTypeFragment_to_signUpProfileFragment)
        }

        binding.signupTypeTravLl.setOnClickListener {
            signUpViewModel.updateIsNav(false)
            navigate(R.id.action_signUpUserTypeFragment_to_signUpProfileFragment)
        }
    }


}