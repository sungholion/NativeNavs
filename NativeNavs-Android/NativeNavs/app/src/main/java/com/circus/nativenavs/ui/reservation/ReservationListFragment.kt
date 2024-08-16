package com.circus.nativenavs.ui.reservation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentReservationListBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.tour.TourListBridge
import com.circus.nativenavs.ui.tour.TourListFragmentDirections
import com.circus.nativenavs.ui.trip.MyTripReservationListFragmentDirections
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate

class ReservationListFragment : BaseFragment<FragmentReservationListBinding>(
    FragmentReservationListBinding::bind,
    R.layout.fragment_reservation_list
) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge : ReservationListBridge
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

    private fun initBridge() {
        bridge = ReservationListBridge(homeActivity, this, binding.reservationListWv)
        binding.reservationListWv.addJavascriptInterface(bridge, "Android")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBridge()

        binding.reservationListWv.let {
            it.settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(false)
                domStorageEnabled = true
            }
            it.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!isPageLoaded) {
                        isPageLoaded = true
                        bridge.sendUserData(UserDto(
                            SharedPref.userId!!,
                            SharedPref.accessToken!!,
                            SharedPref.isNav!!,
                            SharedPref.language == "ko"))
                    }
                }
            }
            binding.reservationListWv.webChromeClient = WebChromeClient()

            binding.reservationListWv.loadUrl(WEBURL + "Trav/${SharedPref.userId}/reservation_list")
        }
    }
    fun navigateToReservationListFragmentReservationDetail(tourId : Int, reservationId : Int){
        val action = ReservationListFragmentDirections.actionReservationListFragmentToReservationDetailFragment(
            tourId = tourId,
            reservationId = reservationId
        )
        navigate(action)
    }
    fun navigateToReservationListFragmentTourList(){
        val action = ReservationListFragmentDirections.actionReservationListFragmentToTourListFragment()
        navigate(action)
    }

}