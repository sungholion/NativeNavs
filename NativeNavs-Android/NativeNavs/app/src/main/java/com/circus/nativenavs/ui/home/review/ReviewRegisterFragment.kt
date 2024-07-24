package com.circus.nativenavs.ui.home.review

import android.content.Context
import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentReviewRegisterBinding
import com.circus.nativenavs.ui.home.HomeActivity

class ReviewRegisterFragment : BaseFragment<FragmentReviewRegisterBinding>(FragmentReviewRegisterBinding::bind,R.layout.fragment_review_register) {

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