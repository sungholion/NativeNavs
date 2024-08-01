package com.circus.nativenavs.ui.home

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityHomeBinding
import com.circus.nativenavs.ui.chat.ChatListFragment
import com.circus.nativenavs.ui.home.mypage.MypageFragment
import com.circus.nativenavs.ui.reservation.ReservationListFragment
import com.circus.nativenavs.ui.tour.TourListFragment
import com.circus.nativenavs.ui.tour.TourRegisterFragment
import com.circus.nativenavs.ui.home.trip.MyTripFragment
import com.circus.nativenavs.ui.login.LoginActivityViewModel
import com.circus.nativenavs.util.LocaleUtils
import com.circus.nativenavs.util.SharedPref

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {

        SharedPref.language?.let {
            LocaleUtils.setLocale(this, it)
        }
        super.onCreate(savedInstanceState)

        initView()

    }

    private fun initView() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_fcv) as NavHostFragment
        val navController = navHostFragment.navController


        NavigationUI.setupWithNavController(binding.mainBottomNav, navController)
        binding.mainBottomNav.setOnClickListener {
            findNavController(R.id.home_fcv).popBackStack()
        }
    }

    fun hideBottomNav(isHide: Boolean) {
        if (!isHide) {
            binding.mainBottomNav.visibility = View.GONE
        } else {
            binding.mainBottomNav.visibility = View.VISIBLE
        }
    }
}