package com.circus.nativenavs.ui.review

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentReviewListBinding
import com.circus.nativenavs.databinding.FragmentReviewPhotoBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.popBackStack

private const val TAG = "ReviewPhotoFragment"

class ReviewPhotoFragment : BaseFragment<FragmentReviewPhotoBinding>(
    FragmentReviewPhotoBinding::bind,
    R.layout.fragment_review_photo
) {

    private lateinit var homeActivity: HomeActivity
    private val args: ReviewListFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToast(args.toString())
        initCustomView()
        initWebView()
    }

    private fun initWebView() {
        var url = ""
        if (args.tourId != -1) {
            url = "https://i11d110.p.ssafy.io/tour/detail/${args.tourId}/reviewphotos"
        } else if (args.navId != -1) {
            url = "https://i11d110.p.ssafy.io/nav/${args.navId}/reviewphotos"
        } else if (args.travId != -1) {
            url = "https://i11d110.p.ssafy.io/trav/${args.travId}/reviewphotos"
        }

        Log.d(TAG, "initCustomView: $url")
        binding.reviewPhotoCustomWv.loadWebViewUrl(url)

    }

    private fun initCustomView() {
        if (args.travId != -1) {
            binding.reviewPhotoCustomWv.setTitle("")
        }

        binding.reviewPhotoCustomWv.setOnBackListener(object :
            CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }

        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.reviewPhotoCustomWv.backWebView()) {
                        popBackStack()
                    }
                }
            })

    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }
}