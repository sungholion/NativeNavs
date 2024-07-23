package com.circus.nativenavs.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpProfileBinding
import com.circus.nativenavs.util.navigate


class SignUpProfileFragment : BaseFragment<FragmentSignUpProfileBinding>(
    FragmentSignUpProfileBinding::bind,
    R.layout.fragment_sign_up_profile
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.signupRegisterBtn.setOnClickListener {
            navigate(R.id.action_signUpProfileFragment_to_signUpCompleteFragment)
        }
    }

}