package com.circus.nativenavs.ui.tour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.NavDirections
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentTourListBinding
import com.circus.nativenavs.ui.chat.KrossbowChattingViewModel
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.ui.video.VideoActivity
import com.circus.nativenavs.util.LocaleUtils
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val TAG = "TourListFragment"

class TourListFragment : BaseFragment<FragmentTourListBinding>(
    FragmentTourListBinding::bind,
    R.layout.fragment_tour_list
) {
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()
    private val chattingViewModel: KrossbowChattingViewModel by activityViewModels()

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: TourListBridge
    private var isPageLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        initWebView()
        initBridge()
        initObserve()
    }

    private fun initObserve() {
        chattingViewModel.chatRoomId.observe(viewLifecycleOwner) { roomId ->
            if (roomId != -1) {
                val action =
                    TourListFragmentDirections.actionTourListFragmentToChattingRoomFragment(
                        chatId = roomId
                    )
                navigate(action)
                chattingViewModel.setChatRoomId(-1)
            }

        }

        homeActivityViewModel.notiFlag.observe(viewLifecycleOwner) { flag ->
            if (flag != -1) {
                var action: NavDirections? = null
                when (flag) {
                    1 -> {
                        if (homeActivityViewModel.notiRoomId.value != -1) {
                            chattingViewModel.setCurrentChatRoom(homeActivityViewModel.notiRoomId.value!!)
                            homeActivityViewModel.setNotiFlag(-1)
                            homeActivityViewModel.setNotiRoomId(-1)
                        }
                    }

                    2 -> {
                        if (homeActivityViewModel.notiReservationId.value != -1 && homeActivityViewModel.notiTourId.value != -1) {
                            action =
                                TourListFragmentDirections.actionTourListFragmentToReservationDetailFragment(
                                    reservationId = homeActivityViewModel.notiReservationId.value!!,
                                    tourId = homeActivityViewModel.notiTourId.value!!
                                )
                            homeActivityViewModel.setNotiFlag(-1)
                            homeActivityViewModel.setNotiReservationId(-1)
                            homeActivityViewModel.setNotiTourId(-1)
                        }
                    }

                    3 -> {
                        if (homeActivityViewModel.notiReservationId.value != -1 && homeActivityViewModel.notiTourId.value != -1) {
                            action =
                                TourListFragmentDirections.actionTourListFragmentToReservationDetailFragment(
                                    reservationId = homeActivityViewModel.notiReservationId.value!!,
                                    tourId = homeActivityViewModel.notiTourId.value!!
                                )
                            homeActivityViewModel.setNotiFlag(-1)
                            homeActivityViewModel.setNotiReservationId(-1)
                            homeActivityViewModel.setNotiTourId(-1)
                        }
                    }

                    4 -> {
                        if (homeActivityViewModel.notiReservationId.value != -1 && homeActivityViewModel.notiTourId.value != -1) {
                            action =
                                TourListFragmentDirections.actionTourListFragmentToReservationDetailFragment(
                                    reservationId = homeActivityViewModel.notiReservationId.value!!,
                                    tourId = homeActivityViewModel.notiTourId.value!!
                                )
                            homeActivityViewModel.setNotiFlag(-1)
                            homeActivityViewModel.setNotiReservationId(-1)
                            homeActivityViewModel.setNotiTourId(-1)
                        }
                    }
                }
                action?.let {
                    navigate(action)
                }

            }

        }
    }

    private fun initBridge() {
        bridge = TourListBridge(homeActivity, this, binding.tourListWv)
        binding.tourListWv.addJavascriptInterface(bridge, "Android")
    }

    private fun initEvent() {
        binding.tourSearchBtn.setOnClickListener {
            navigate(R.id.action_tourListFragment_to_tourSearchFragment)
        }
    }

    private fun initWebView() {
        binding.tourListWv.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            domStorageEnabled = true
        }

        binding.tourListWv.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!isPageLoaded) {
                    isPageLoaded = true
                    bridge.sendUserData(UserDto(
                        SharedPref.userId!!,
                        SharedPref.accessToken!!,
                        SharedPref.isNav!!,
                        SharedPref.language == "ko"))
                    homeActivityViewModel.let {
                        bridge.sendSearchData(
                            it.searchTravel.value!!,
                            it.searchDate.value!!,
                            it.searchTheme.value!!
                        )
                    }
                }
            }
        }
        binding.tourListWv.webChromeClient = WebChromeClient()

        binding.tourListWv.loadUrl(WEBURL + "main")
    }

    fun moveToTourDetailFragment(tourId: Int, navId: Int) {
        val action = TourListFragmentDirections.actionTourListFragmentToTourDetailFragment(
            tourId = tourId,
            navId = navId
        )
        navigate(action)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
        isPageLoaded = false
    }

    override fun onPause() {
        super.onPause()
        homeActivityViewModel.setNotiFlag(-1)
        chattingViewModel.setChatRoomId(-1)
    }
}
