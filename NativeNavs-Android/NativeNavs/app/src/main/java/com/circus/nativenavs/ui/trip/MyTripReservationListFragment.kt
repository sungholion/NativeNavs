package com.circus.nativenavs.ui.trip

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
import com.circus.nativenavs.databinding.FragmentMyTripReservationListBinding
import com.circus.nativenavs.databinding.FragmentReservationListBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourDetailBridge
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

private const val TAG = "MyTripReservationListFr"

class MyTripReservationListFragment : BaseFragment<FragmentMyTripReservationListBinding>(
    FragmentMyTripReservationListBinding::bind,
    R.layout.fragment_my_trip_reservation_list
) {

    private lateinit var homeActivity: HomeActivity
    private val args: MyTripReservationListFragmentArgs by navArgs()

    private lateinit var bridge: MyTripReservationListBridge
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

    private fun initWebView() {
        val url = WEBURL + "reservation/${args.tourId}/list"
        Log.d(TAG, "initCustomView: $url")
        binding.myTripReservationListWv.loadWebViewUrl(url)

    }

    fun navigateToReservationDetailFragment(reservationId: Int) {
        val action =
            MyTripReservationListFragmentDirections.actionMyTripReservationListFragmentToReservationDetailFragment(
                tourId = args.tourId,
                reservationId = reservationId
            )
        navigate(action)
    }

    private fun initCustomView() {
        binding.myTripReservationListWv.setOnBackListener(object :
            CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.myTripReservationListWv.backWebView()) {
                        popBackStack()
                    }
                }
            })

    }

    private fun initBridge() {
        bridge = MyTripReservationListBridge(
            homeActivity,
            this,
            binding.myTripReservationListWv.binding.customWebviewTitleWv
        )
        binding.myTripReservationListWv.binding.customWebviewTitleWv.addJavascriptInterface(
            bridge,
            "Android"
        )
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
        isPageLoaded = false
    }

}