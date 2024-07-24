package com.circus.nativenavs.ui.notification

import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentNotificationBinding
import com.circus.nativenavs.ui.signup.SignUpLanguageFragment.Companion.COUNTRIES
import com.circus.nativenavs.util.popBackStack

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding::bind,
    R.layout.fragment_notification
) {

    private val notiListAdapter = NotiListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()
    }

    private fun initEvent() {
        binding.notiTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }
    }

    private fun initAdapter() {
        binding.notiRv.adapter = notiListAdapter
        notiListAdapter.submitList(COUNTRIES)
    }

}