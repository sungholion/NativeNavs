package com.circus.nativenavs.ui.reservation

import android.content.Context
import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentReservationListBinding
import com.circus.nativenavs.ui.home.HomeActivity

class ReservationListFragment : BaseFragment<FragmentReservationListBinding>(FragmentReservationListBinding::bind,R.layout.fragment_reservation_list) {

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}