package com.circus.nativenavs.ui.team

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTeamIntroBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.popBackStack

class TeamIntroFragment : BaseFragment<FragmentTeamIntroBinding>(
    FragmentTeamIntroBinding::bind,
    R.layout.fragment_team_intro
) {

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

        binding.teamCustomWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener{
            override fun onClick() {
                popBackStack()
            }

        })

        homeActivity.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(!binding.teamCustomWv.backWebView()){
                    popBackStack()
                }
            }

        })
    }
    private fun initWebView(){
        binding.teamCustomWv.loadWebViewUrl(WEBURL + "team")
    }

}