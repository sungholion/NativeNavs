package com.circus.nativenavs.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.signup.SignUpActivityViewModel
import com.circus.nativenavs.databinding.FragmentSignUpEmailBinding
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

class SignUpEmailFragment : BaseFragment<FragmentSignUpEmailBinding>(
    FragmentSignUpEmailBinding::bind,
    R.layout.fragment_sign_up_email
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

        binding.signupEmailNextBtn.setOnClickListener {
            val email = binding.signupEmailEt.text.toString()
            val password = binding.signupPwEt.text.toString()
            signUpViewModel.updateEmail(email)
            signUpViewModel.updatePassword(password)
            navigate(R.id.action_signUpEmailFragment_to_signUpUserTypeFragment)
        }
    }

}