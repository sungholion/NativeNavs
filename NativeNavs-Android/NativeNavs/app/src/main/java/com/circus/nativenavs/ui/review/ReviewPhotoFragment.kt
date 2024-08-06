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
import com.circus.nativenavs.databinding.FragmentReviewPhotoBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.popBackStack

private const val TAG = "ReviewPhotoFragment"

class ReviewPhotoFragment : BaseFragment<FragmentReviewPhotoBinding>(
    FragmentReviewPhotoBinding::bind,
    R.layout.fragment_review_photo
) {

    private lateinit var homeActivity: HomeActivity
    private val args: ReviewPhotoFragmentArgs by navArgs()

    private var isPageLoaded = false
    private lateinit var bridge: ReviewPhotoBridge

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToast(args.toString())
        initBridge()
        initCustomView()
        initWebView()
    }

    private fun initBridge() {
        bridge = ReviewPhotoBridge(homeActivity, this, binding.reviewPhotoCustomWv.binding.customWebviewTitleWv)
    }

    private fun initWebView() {
        binding.reviewPhotoCustomWv.binding.customWebviewTitleWv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!isPageLoaded) {
                    isPageLoaded = true
                    bridge.sendUserData(
                        UserDto(
                            SharedPref.userId!!,
                            SharedPref.accessToken!!,
                            SharedPref.isNav!!
                        )
                    )
                }
            }
        }


        var url = ""
        if (args.tourId != -1) {
            url = WEBURL + "tour/detail/${args.tourId}/reviewphotos"
        } else if (args.navId != -1) {
            url = WEBURL + "nav/${args.navId}/reviewphotos"
        } else if (args.travId != -1) {
            url = WEBURL + "trav/${args.travId}/reviewphotos"
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
        isPageLoaded = false
    }
}