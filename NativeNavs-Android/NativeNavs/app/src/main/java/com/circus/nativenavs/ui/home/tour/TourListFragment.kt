package com.circus.nativenavs.ui.home.tour

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.circus.nativenavs.R
import com.circus.nativenavs.config.ApplicationClass.Companion.FRAGMENT_TOUR_LIST
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourListBinding


class TourListFragment : BaseFragment<FragmentTourListBinding>(FragmentTourListBinding::bind,R.layout.fragment_tour_list) {

    private val menu_code = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchBar()

    }

    private fun initSearchBar(){
//        when(menu_code){
//            FRAGMENT_TOUR_LIST -> binding.tourSearchCv.visibility = VISIBLE
//            else -> binding.tourSearchCv.visibility = GONE
//        }
    }

    companion object{

    }

}