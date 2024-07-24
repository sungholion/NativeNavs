package com.circus.nativenavs.ui.home

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityHomeBinding
import com.circus.nativenavs.ui.home.chat.ChatListFragment
import com.circus.nativenavs.ui.home.mypage.MypageFragment
import com.circus.nativenavs.ui.home.reservation.ReservationListFragment
import com.circus.nativenavs.ui.home.tour.TourListFragment
import com.circus.nativenavs.ui.home.tour.TourRegisterFragment
import com.circus.nativenavs.ui.home.trip.MyTripFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    var type = true

    override fun onCreate(savedInstanceState: Bundle?) {
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

}