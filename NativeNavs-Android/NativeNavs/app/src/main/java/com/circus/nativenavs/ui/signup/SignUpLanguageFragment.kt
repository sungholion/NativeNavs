package com.circus.nativenavs.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpLanguageBinding
import com.circus.nativenavs.util.popBackStack


class SignUpLanguageFragment : BaseFragment<FragmentSignUpLanguageBinding>(
    FragmentSignUpLanguageBinding::bind,
    R.layout.fragment_sign_up_language
) {
    private var count = 0
    private val signUpViewModel: SignUpActivityViewModel by activityViewModels()
    private val languageListAdapter by lazy {
        LanguageListAdapter({ language, isChecked ->
            val updatedLanguages =
                signUpViewModel.languageList.value?.language?.toMutableList()?.apply {
                    if (isChecked) {
                        add(language)
                    } else {
                        remove(language)
                    }
                }
            signUpViewModel.updateCheckList(language, isChecked)
            updatedLanguages?.let { signUpViewModel.updateLanguage(it) }

        }, count)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        signUpViewModel.languageCheckList.value?.forEach {
            if (it.isChecked) count++
        }

        initAdapter()
        initEvent()
        signUpViewModel.languageList.observe(viewLifecycleOwner) { languageList ->
            val selectedLanguages = languageList.language
            count.apply {
                signUpViewModel.languageCheckList.value?.forEach {
                    if (it.isChecked) count++
                }
            }

            val updatedList = languageListAdapter.currentList.map { language ->
                language.copy(isChecked = selectedLanguages.contains(language.language))
            }

            languageListAdapter.submitList(updatedList)
        }

    }
private fun initView(){
    binding.signupTitleLayout.titleText = getString(R.string.sign_languages)
}

    private fun initAdapter() {
        binding.signupLanguageRv.adapter = languageListAdapter
        languageListAdapter.submitList(signUpViewModel.languageCheckList.value)
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
            "USA",
            "Korea",
            "Japan",
            "China",
            "France",
            "Italy",
            "Germany",
            "Russia",
            "Saudi Arabia",
            "Spain"
        )

    }
}