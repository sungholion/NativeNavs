package com.circus.nativenavs.ui.review

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentReviewListBinding
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.popBackStack
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourDetailBridge
import com.circus.nativenavs.ui.tour.TourDetailFragmentArgs
import com.circus.nativenavs.util.navigate

private const val TAG = "ReviewListFragment"

class ReviewListFragment : BaseFragment<FragmentReviewListBinding>(
    FragmentReviewListBinding::bind,
    R.layout.fragment_review_list
) {

    private lateinit var homeActivity: HomeActivity
    private val args: ReviewListFragmentArgs by navArgs()

    private lateinit var bridge: ReviewListBridge
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBridge()
        initCustomView()
        initWebView()
    }

    private fun initBridge() {
        bridge = ReviewListBridge(
            homeActivity,
            this,
            binding.reviewCustomWv.binding.customWebviewTitleWv
        )

        binding.reviewCustomWv.binding.customWebviewTitleWv.addJavascriptInterface(
            bridge,
            "Android"
        )
    }

    private fun initWebView() {
        var url = ""
        if (args.tourId != -1) {
            url = "https://i11d110.p.ssafy.io/tour/detail/${args.tourId}/reviews"
        } else if (args.travId != -1) {
            url = "https://i11d110.p.ssafy.io/nav/${args.navId}/reviews"
        } else if (args.navId != -1) {
            url = "https://i11d110.p.ssafy.io/trav/${args.navId}/reviews"
        }

        Log.d(TAG, "initCustomView: $url")
        binding.reviewCustomWv.loadWebViewUrl(url)

    }

    fun navigateToTourReviewPhotoFragment(tourId: Int) {
        val action =
            ReviewListFragmentDirections.actionReviewListFragmentToReviewPhotoFragment(tourId = tourId)
        navigate(action)
    }

    fun navigateToNavReviewPhotoFragment(navId: Int) {
        val action =
            ReviewListFragmentDirections.actionReviewListFragmentToReviewPhotoFragment(navId = navId)
        navigate(action)
    }

    fun navigateToTravReviewPhotoFragment(travId: Int) {
        val action =
            ReviewListFragmentDirections.actionReviewListFragmentToReviewPhotoFragment(travId = travId)
        navigate(action)
    }

    private fun initCustomView() {
        binding.reviewCustomWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }

        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.reviewCustomWv.backWebView()) {
                        popBackStack()
                    }
                }
            })

    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
        isPageLoaded = false
    }
}
