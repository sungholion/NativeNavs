package com.circus.nativenavs.ui.home.reservation

import android.content.Context
import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentReservationRegisterBinding
import com.circus.nativenavs.ui.home.HomeActivity

class ReservationRegisterFragment : BaseFragment<FragmentReservationRegisterBinding>(FragmentReservationRegisterBinding::bind,R.layout.fragment_reservation_register) {

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

    }
}