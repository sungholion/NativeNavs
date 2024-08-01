package com.circus.nativenavs.ui.home

import android.os.Bundle
import android.util.Log
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
import com.circus.nativenavs.ui.login.LoginActivityViewModel
import com.circus.nativenavs.util.LocaleUtils
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.ui.trip.MyTripFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        initView()

        Log.d("HomeActivity", "onCreate: userId ${SharedPref.userId} isNav ${SharedPref.isNav}")

    }

    private fun initView() {
        if(SharedPref.isNav == true) binding.mainBottomNav.inflateMenu(R.menu.menu_bottom_nav_navs)
        else binding.mainBottomNav.inflateMenu(R.menu.menu_bottom_nav_trav)

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