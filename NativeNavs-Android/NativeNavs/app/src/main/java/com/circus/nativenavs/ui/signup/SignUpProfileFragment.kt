package com.circus.nativenavs.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpProfileBinding
import com.circus.nativenavs.ui.setting.CustomSpinnerAdapter
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

class SignUpProfileFragment : BaseFragment<FragmentSignUpProfileBinding>(
    FragmentSignUpProfileBinding::bind,
    R.layout.fragment_sign_up_profile
) {

    private lateinit var signUpActivity: SignUpActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = context as SignUpActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initSpinner()
    }

    private fun initSpinner() {
        val spinnerAdapter = CustomSpinnerAdapter(signUpActivity, SignUpLanguageFragment.COUNTRIES)
        binding.signupNationalitySp.adapter = spinnerAdapter
        binding.signupNationalitySp.setSelection(0)

        val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerAdapter.setSelectedItemPosition(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinnerAdapter.setSelectedItemPosition(0)
            }

        }

        binding.signupNationalitySp.onItemSelectedListener = spinnerItemSelectedListener
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