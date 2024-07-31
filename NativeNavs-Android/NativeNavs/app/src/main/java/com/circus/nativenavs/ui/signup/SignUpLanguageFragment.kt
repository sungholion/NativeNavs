package com.circus.nativenavs.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.LanguageDTO
import com.circus.nativenavs.databinding.FragmentSignUpLanguageBinding
import com.circus.nativenavs.util.popBackStack


class SignUpLanguageFragment : BaseFragment<FragmentSignUpLanguageBinding>(
    FragmentSignUpLanguageBinding::bind,
    R.layout.fragment_sign_up_language
) {

    private val signUpViewModel: SignUpActivityViewModel by activityViewModels()
    val languageList = COUNTRIES.map { LanguageDTO(it, isChecked = false) }
    private val languageListAdapter = LanguageListAdapter{ language, isChecked ->
        val updatedLanguages = signUpViewModel.languageList.value?.language?.toMutableList()?.apply {
            if (isChecked) add(language) else remove(language)
        }
        updatedLanguages?.let { signUpViewModel.updateLanguage(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()

        // 선택된 언어를 observe
        signUpViewModel.languageList.observe(viewLifecycleOwner) { languageList ->
            val selectedLanguages = languageList.language


            // 기존 리스트에서 체크 상태를 업데이트
            val updatedList = languageListAdapter.currentList.map { language ->
                language.copy(isChecked = selectedLanguages.contains(language.language))
            }

            // 업데이트된 리스트를 어댑터에 제출
            languageListAdapter.submitList(updatedList)
        }

    }

    private fun initAdapter() {
        binding.signupLanguageRv.adapter = languageListAdapter
        languageListAdapter.submitList(languageList)
    }

    private fun initEvent() {
        binding.signupTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.signupLanguageRegisterBtn.setOnClickListener {
            popBackStack()
        }
    }

    companion object {
        val COUNTRIES = arrayListOf(
            "English",
            "Korean",
            "Japanese",
            "Chinese",
            "French",
            "Italian",
            "German",
            "Russian",
            "Arabic",
            "Spanish"
        )

    }
}