package com.circus.nativenavs.ui.tos

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTosBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.popBackStack


class TosFragment : BaseFragment<FragmentTosBinding>(FragmentTosBinding::bind,R.layout.fragment_tos) {

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

        initWebView()

        binding.tosCustomWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener{
            override fun onClick() {
                popBackStack()
            }

        })

        homeActivity.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(!binding.tosCustomWv.backWebView()){
                    popBackStack()
                }
            }

        })
    }
    private fun initWebView(){
        binding.tosCustomWv.loadWebViewUrl(WEBURL + "privacy_terms")
    }
}