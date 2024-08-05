package com.circus.nativenavs.ui.setting

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentAppSettingBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.LocaleUtils
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import java.util.Locale


class AppSettingFragment : BaseFragment<FragmentAppSettingBinding>(
    FragmentAppSettingBinding::bind,
    R.layout.fragment_app_setting
) {

    private lateinit var homeActivity: HomeActivity
    private val appSettingViewModel: HomeActivityViewModel by activityViewModels()
    private var start = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        println("새로 생성됨")
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initSpinner()

    }

    private fun initSpinner() {
        val items = arrayListOf("한국어", "English")
        val spinnerAdapter = CustomSpinnerAdapter(homeActivity, items)
        binding.settingLanguageSp.adapter = spinnerAdapter

        val savedLanguage = SharedPref.language
        println("초기 : "+ SharedPref.language)
        val initialPosition = if (savedLanguage == "ko") 0 else 1
        binding.settingLanguageSp.setSelection(initialPosition)

        val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = when (position) {
                    0 -> "ko"  // 한국어
                    1 -> "en"  // 영어
                    else -> "ko"  // 기본 언어
                }
                if (SharedPref.language != selectedLanguage) {
                    SharedPref.language = selectedLanguage
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                    activity?.finish()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.settingLanguageSp.onItemSelectedListener = spinnerItemSelectedListener
    }

    private fun initEvent() {
        binding.settingTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }
    }

}