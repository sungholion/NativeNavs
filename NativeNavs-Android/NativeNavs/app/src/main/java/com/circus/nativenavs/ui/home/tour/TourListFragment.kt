package com.circus.nativenavs.ui.home.tour

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourListBinding
import com.circus.nativenavs.ui.home.HomeActivity


class TourListFragment : BaseFragment<FragmentTourListBinding>(FragmentTourListBinding::bind,R.layout.fragment_tour_list) {

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

        binding.tourSearchBtn.setOnClickListener {
            Toast.makeText(requireContext(),"검색바 클릭",Toast.LENGTH_SHORT).show()
        }
    }

}