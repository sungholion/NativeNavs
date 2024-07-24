package com.circus.nativenavs.ui.home.review

import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentReviewListBinding
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.popBackStack


class ReviewListFragment : BaseFragment<FragmentReviewListBinding>(FragmentReviewListBinding::bind, R.layout.fragment_review_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reviewCustomWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener{
            override fun onClick() {
                popBackStack()
            }

        })

        binding.reviewCustomWv.loadWebViewUrl("www.naver.com")
    }

}