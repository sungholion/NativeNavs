package com.circus.nativenavs.ui.tour

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourModifyBinding
import com.circus.nativenavs.ui.home.HomeActivity

class TourModifyFragment : BaseFragment<FragmentTourModifyBinding>(FragmentTourModifyBinding::bind, R.layout.fragment_tour_modify) {

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