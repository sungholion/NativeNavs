package com.circus.nativenavs.ui.notification

import android.content.Context
import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentNotificationBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.signup.SignUpLanguageFragment.Companion.COUNTRIES
import com.circus.nativenavs.util.popBackStack

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    FragmentNotificationBinding::bind,
    R.layout.fragment_notification
) {

    private val notiListAdapter = NotiListAdapter()

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initEvent()
    }

    private fun initView(){
        binding.notiTitleLayout.titleText = getString(R.string.noti_title)
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