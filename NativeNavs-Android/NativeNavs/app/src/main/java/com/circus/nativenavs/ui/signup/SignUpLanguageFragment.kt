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
    var languageList = emptyList<LanguageDTO>()


    private val languageListAdapter = LanguageListAdapter{ language, isChecked ->
        val updatedLanguages = signUpViewModel.languageSelectList.value?.toMutableList()?.apply {
            if (isChecked) add(language) else remove(language)
        }
        updatedLanguages?.let { signUpViewModel.updateUserLanguage(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()
        signUpViewModel.getLanguage()

        signUpViewModel.languageList.observe(viewLifecycleOwner) { getList ->
            languageList = getList.map { LanguageDTO(it.language, isChecked = false) }

            // 업데이트된 리스트를 어댑터에 제출
            languageListAdapter.submitList(languageList)
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

    }
}