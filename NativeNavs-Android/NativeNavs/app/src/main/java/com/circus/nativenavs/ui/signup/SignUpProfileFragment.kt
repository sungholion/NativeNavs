package com.circus.nativenavs.ui.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpProfileBinding
import com.circus.nativenavs.ui.setting.CustomSpinnerAdapter
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale

class SignUpProfileFragment : BaseFragment<FragmentSignUpProfileBinding>(
    FragmentSignUpProfileBinding::bind,
    R.layout.fragment_sign_up_profile
) {

    private lateinit var signUpActivity: SignUpActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = context as SignUpActivity
    }

    private var nickname =""
    private val signUpViewModel: SignUpActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initEvent()
        initSpinner()
        initTextWatcher()

        signUpViewModel.languageList.observe(viewLifecycleOwner) { languageList ->
            binding.signupSelectedLanguageTv.text = languageList.language.joinToString(", ")
        }

        signUpViewModel.signStatus.observe(viewLifecycleOwner) { statusCode ->
            when (statusCode) {
                202 -> {
                    Toast.makeText(requireContext(), "회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    navigate(R.id.action_signUpProfileFragment_to_signUpCompleteFragment)
                }

                else -> showToast("회원 가입 실패")
            }

        }

        signUpViewModel.dupliState.observe(viewLifecycleOwner) { status ->
            when (status.first) {
                200 -> {
                    signUpViewModel.updateNicknameCheck(true)
                    binding.signupDupliOk.visibility = VISIBLE
                    binding.signupDupliNone.visibility = INVISIBLE
                    binding.signupDupliBad.visibility = INVISIBLE
                    binding.signupNicknameHelpTv.visibility = INVISIBLE
                    signUpViewModel.updateNickname(nickname)
                }

                else -> {
                    binding.signupDupliOk.visibility = INVISIBLE
                    binding.signupDupliNone.visibility = INVISIBLE
                    binding.signupDupliBad.visibility = VISIBLE
                    binding.signupNicknameHelpTv.visibility = INVISIBLE
                    signUpViewModel.updateNickname(nickname)
                }
            }

        }

    }

    private fun initTextWatcher() {
        binding.signupNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (signUpViewModel.signUpDTO.value?.nickname != binding.signupNicknameEt.text.toString())
                    signUpViewModel.updateNicknameCheck(false)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.signupBirthEt.addTextChangedListener(object : TextWatcher {
            private var isUpdating: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                isUpdating = true
                var text = s.toString().replace(Regex("[^\\d]"), "") // 숫자만 남기기

                if (text.length > 4) {
                    text = text.substring(0, 4) + "-" + text.substring(4)
                }
                if (text.length > 7) {
                    text = text.substring(0, 7) + "-" + text.substring(7)
                }
                if (text.length > 10) {
                    text = text.substring(0, 10)
                }
                binding.signupBirthEt.setText(text)
                binding.signupBirthEt.setSelection(text.length) // 커서를 텍스트 끝으로 이동

                isUpdating = false
            }
        }
        )
    }

    private fun initView() {

        if (signUpViewModel.nicknameCheck.value == true) {
            binding.signupDupliOk.visibility = VISIBLE
            binding.signupDupliBad.visibility = INVISIBLE
            binding.signupDupliNone.visibility = INVISIBLE
            binding.signupNicknameHelpTv.visibility = INVISIBLE
        } else {
            binding.signupDupliOk.visibility = INVISIBLE
            binding.signupDupliBad.visibility = INVISIBLE
            binding.signupDupliNone.visibility = INVISIBLE
            binding.signupNicknameHelpTv.visibility = VISIBLE
        }
        binding.signupBirthValidTv.visibility = INVISIBLE

    }

    private fun initSpinner() {
        val spinnerAdapter = CustomSpinnerAdapter(signUpActivity, SignUpLanguageFragment.COUNTRIES)
        binding.signupNationalitySp.adapter = spinnerAdapter
        binding.signupNationalitySp.setSelection(0)

        val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerAdapter.setSelectedItemPosition(position)
                signUpViewModel.updateNation(SignUpLanguageFragment.COUNTRIES[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinnerAdapter.setSelectedItemPosition(0)
            }

        }

        binding.signupNationalitySp.onItemSelectedListener = spinnerItemSelectedListener
    }

    private fun initValid() {
        binding.signupNameHelpTv.visibility = VISIBLE
        binding.signupNameValidTv.visibility = INVISIBLE
        binding.signupBirthValidTv.visibility = INVISIBLE
        binding.signupPhoneHelpTv.visibility = VISIBLE
        binding.signupPhoneValidTv.visibility = INVISIBLE
    }

    fun isValidDate(dateString: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return try {
            val date = LocalDate.parse(dateString, formatter)

            // 월이 1월부터 12월 사이인지 확인합니다.
            val isCorrectMonth = date.monthValue in 1..12
            // 일이 1일부터 해당 월의 마지막 날까지 유효한지 확인합니다.
            val isCorrectDay = date.dayOfMonth in 1..date.lengthOfMonth()

            isCorrectMonth && isCorrectDay
        } catch (e: DateTimeParseException) {
            // 날짜 파싱에 실패하면 false를 반환합니다.
            false
        }
    }

    private fun initEvent() {
        binding.signupTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }


        binding.signupNicknameCheckBtn.setOnClickListener {

            nickname = binding.signupNicknameEt.text.toString()
            if (nickname == "") {
                binding.signupDupliNone.visibility = VISIBLE
                binding.signupDupliOk.visibility = INVISIBLE
                binding.signupDupliBad.visibility = INVISIBLE
                binding.signupNicknameHelpTv.visibility = INVISIBLE
            } else {
                signUpViewModel.isDupli(nickname)
            }

        }

        binding.signupRegisterBtn.setOnClickListener {
            initValid()

            val name = binding.signupNameEt.text.toString()
            val birth = binding.signupBirthEt.text.toString()
            val phone = binding.signupPhoneEt.text.toString()
            val userLanguage = binding.signupSelectedLanguageTv.text.toString()
            val locale: Locale = Locale.getDefault()
            val language: String = locale.language

            if (language == "ko") signUpViewModel.updateIsKorean(true)
            else signUpViewModel.updateIsKorean(false)

            if (signUpViewModel.nicknameCheck.value != true) {
                Toast.makeText(requireContext(), "닉네임 중복 확인을 클릭해주세요", Toast.LENGTH_SHORT).show()
            } else if (name == "") {
                binding.signupNameHelpTv.visibility = INVISIBLE
                binding.signupNameValidTv.visibility = VISIBLE
            } else if (birth == "" || !isValidDate(birth)) {
                binding.signupBirthValidTv.visibility = VISIBLE
            } else if (phone == "") {
                binding.signupPhoneHelpTv.visibility = INVISIBLE
                binding.signupPhoneValidTv.visibility = VISIBLE
            } else if (!binding.signupTermsCb.isChecked) {
                Toast.makeText(requireContext(), "이용 동의를 해주세요", Toast.LENGTH_SHORT).show()
            } else {
                signUpViewModel.updateName(name)
                signUpViewModel.updateBirth(birth)
                signUpViewModel.updatePhone(phone)
                signUpViewModel.updateUserLanguage(userLanguage)
                println(signUpViewModel.toString())
                signUpViewModel.signUp()

            }
        }

        binding.signupLanguageCl.setOnClickListener {
            navigate(R.id.action_signUpProfileFragment_to_signUpLanguageFragment)
        }

        binding.signupTermsTv.setOnClickListener {
            navigate(R.id.action_signUpProfileFragment_to_signUpTosFragment)
        }
    }

}
