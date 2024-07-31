package com.circus.nativenavs.ui.review

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.trip.MyTripFragment

private const val TAG = "ReviewListBridge"

class ReviewListBridge(
    private val homeActivity: HomeActivity,
    private val fragment: ReviewListFragment,
    private val webView: WebView
) {

    @JavascriptInterface
    fun navigateToTourReviewPhotoFragment(tourId: Int) {
        fragment.navigateToTourReviewPhotoFragment(tourId)
        Log.d(TAG, "navigateToTourReviewPhotoFragment: $tourId")
    }

    @JavascriptInterface
    fun navigateToNavReviewPhotoFragment(navId: Int) {
        fragment.navigateToNavReviewPhotoFragment(navId)
        Log.d(TAG, "navigateToNavReviewPhotoFragment: $navId")
    }

    @JavascriptInterface
    fun navigateToTravReviewPhotoFragment(travId: Int) {
        fragment.navigateToTravReviewPhotoFragment(travId)
        Log.d(TAG, "navigateToTravReviewPhotoFragment: $travId")
    }
}