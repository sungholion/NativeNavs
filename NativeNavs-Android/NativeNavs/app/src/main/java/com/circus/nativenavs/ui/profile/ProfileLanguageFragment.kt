package com.circus.nativenavs.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentSignUpLanguageBinding
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.ui.signup.LanguageListAdapter
import com.circus.nativenavs.ui.signup.SignUpActivityViewModel
import com.circus.nativenavs.util.popBackStack

class ProfileLanguageFragment : BaseFragment<FragmentSignUpLanguageBinding>(
    FragmentSignUpLanguageBinding::bind,
    R.layout.fragment_sign_up_language
) {
    private var count = 0
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()
    private val languageListAdapter by lazy {
        LanguageListAdapter({ language, isChecked ->
            val updatedLanguages =
                homeActivityViewModel.languageList.value?.language?.toMutableList()?.apply {
                    if (isChecked) {
                        add(language)
                    } else {
                        remove(language)
                    }
                }
            homeActivityViewModel.updateCheckList(language, isChecked)
            updatedLanguages?.let { homeActivityViewModel.updateLanguage(it) }

        }, count)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeActivityViewModel.languageCheckList.value?.forEach {
            if (it.isChecked) count++
        }
        initView()
        initAdapter()
        initEvent()
        initObserve()

    }
    private fun initView(){
        binding.signupTitleLayout.titleText = getString(R.string.sign_languages)
    }
    private fun initObserve() {
        homeActivityViewModel.languageList.observe(viewLifecycleOwner) { languageList ->
            val selectedLanguages = languageList.language
            count.apply {
                homeActivityViewModel.languageCheckList.value?.forEach {
                    if (it.isChecked) count++
                }
            }

            val updatedList = languageListAdapter.currentList.map { language ->
                language.copy(isChecked = selectedLanguages.contains(language.language))
            }

            languageListAdapter.submitList(updatedList)
        }
    }

    private fun initAdapter() {
        binding.signupLanguageRv.adapter = languageListAdapter
        languageListAdapter.submitList(homeActivityViewModel.languageCheckList.value)
    }

    private fun initEvent() {
        binding.signupTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.signupLanguageRegisterBtn.setOnClickListener {
            popBackStack()
        }
    }

}