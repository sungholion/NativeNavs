package com.circus.nativenavs.ui.home

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        supportFragmentManager.beginTransaction().replace(R.id.home_fcv, TourListFragment())
            .commit()

        initBottomNavBar()
    }

    private fun initBottomNavBar() {

        binding.mainBottomNav.menu.clear()

        binding.mainBottomNav.apply {

            inflateMenu(R.menu.menu_bottom_nav_trav)

            setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.menu_homeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, TourListFragment()).commit()
                    }
                    R.id.menu_wishlistFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, TourListFragment()).commit()
                    }
                    R.id.menu_tripFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, MyTripFragment()).commit()
                    }
                    R.id.menu_chatFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, ChatListFragment()).commit()
                    }
                    R.id.menu_profileFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, MypageFragment()).commit()
                    }
                    R.id.menu_reservationFragment ->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, ReservationListFragment()).commit()
                    }
                    R.id.menu_tripWriteFragment ->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.home_fcv, TourRegisterFragment()).commit()
                    }
                }
                true
            }
        }

    }
}