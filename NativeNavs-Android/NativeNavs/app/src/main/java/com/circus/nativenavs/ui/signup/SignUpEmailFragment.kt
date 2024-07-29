package com.circus.nativenavs.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpEmailBinding
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import java.util.regex.Pattern

class SignUpEmailFragment : BaseFragment<FragmentSignUpEmailBinding>(
    FragmentSignUpEmailBinding::bind,
    R.layout.fragment_sign_up_email
) {

    private val signUpViewModel: SignUpActivityViewModel by activityViewModels()
    private val emailPattern: Pattern = Patterns.EMAIL_ADDRESS
    private var emailBtnType : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView(){
        if(signUpViewModel.signUpDTO.value?.email?.let { emailPattern.matcher(it).matches() } == true){
            binding.signupCodeSendBtn.text = "재입력"
            binding.signupEmailEt.isEnabled = false
            binding.signupPwCheckEt.isEnabled = true
            binding.signupPwEt.isEnabled = true
            binding.signupEmailNextBtn.isEnabled = true
        }
        else {
            binding.signupCodeSendBtn.text = "전송"
            binding.signupEmailEt.isEnabled = true
        }
    }

    private fun allBtnUnEnabled(){
        binding.signupCodeAuthBtn.isEnabled = false
        binding.signupEmailNextBtn.isEnabled = false
        binding.signupCodeEt.isEnabled = false
        binding.signupPwCheckEt.isEnabled = false
        binding.signupPwEt.isEnabled = false
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-zA-Z0-9])(?=.*[\\W_])[a-zA-Z0-9\\W_]{8,20}\$"
        return password.matches(passwordRegex.toRegex())
    }

    private fun initEvent() {
        var email = ""
        var password = ""
        binding.signupTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }
        /** 이메일 전송 **/
        binding.signupCodeSendBtn.setOnClickListener {
            email = binding.signupEmailEt.text.toString()

            if(!emailBtnType){
                if(!emailPattern.matcher(email).matches()){
                    binding.signupEmailHint.visibility = VISIBLE
                }
                else{
                    binding.signupCodeSendBtn.text = "재입력"
                    binding.signupEmailEt.isEnabled = false
                    binding.signupCodeEt.isEnabled = true
                    binding.signupCodeAuthBtn.isEnabled = true
                    binding.signupEmailHint.visibility = GONE

                    emailBtnType = true
                }
            }
            else{
                allBtnUnEnabled()
                binding.signupCodeSendBtn.text = "전송"
                binding.signupEmailEt.isEnabled = true
                binding.signupCodeEt.isEnabled = false
                binding.signupCodeAuthBtn.isEnabled = false
                emailBtnType = false
            }
        }
        /** 코드 인증 **/
        binding.signupCodeAuthBtn.setOnClickListener {

            // 코드 인증을 마쳐야 비밀 번호 활성화
            binding.signupCodeEt.isEnabled = false
            binding.signupEmailNextBtn.isEnabled = true
            binding.signupPwCheckEt.isEnabled = true
            binding.signupPwEt.isEnabled = true
        }

        binding.signupEmailNextBtn.setOnClickListener {
            email = binding.signupEmailEt.text.toString()
            password = binding.signupPwEt.text.toString()
            val passwordCheck = binding.signupPwCheckEt.text.toString()

            binding.signupPwValidTv.visibility = INVISIBLE
            binding.signupPwHelpTv.visibility = VISIBLE
            binding.signupPwCheckHelpTv.visibility = VISIBLE
            binding.signupPwCheckValidTv.visibility = INVISIBLE

            if(!isPasswordValid(password)){
                binding.signupPwValidTv.visibility = VISIBLE
                binding.signupPwHelpTv.visibility = INVISIBLE
            }
            else{
                if(password != passwordCheck){
                    binding.signupPwCheckHelpTv.visibility = INVISIBLE
                    binding.signupPwCheckValidTv.visibility = VISIBLE
                }
                else{
                    signUpViewModel.updateEmail(email)
                    signUpViewModel.updatePassword(password)
                    navigate(R.id.action_signUpEmailFragment_to_signUpUserTypeFragment)
                }
            }
        }
    }

}