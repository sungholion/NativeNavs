package com.circus.nativenavs.ui.reservation

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
import com.circus.nativenavs.databinding.FragmentReservationRegisterBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import com.google.android.material.dialog.MaterialAlertDialogBuilder

const val TAG = "ReservationRegister"

class ReservationRegisterFragment : BaseFragment<FragmentReservationRegisterBinding>(
    FragmentReservationRegisterBinding::bind,
    R.layout.fragment_reservation_register
) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: ReservationRegisterBridge
    private var isPageLoaded = false
    private val args: ReservationRegisterFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        isPageLoaded = false
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBridge()
        initWebView()
        initCustomView()

    }

    private fun initBridge() {
        bridge =
            ReservationRegisterBridge(
                homeActivity,
                this,
                binding.reservationRegisterCustomWv.binding.customWebviewTitleWv
            )
        binding.reservationRegisterCustomWv.binding.customWebviewTitleWv.addJavascriptInterface(
            bridge,
            "Android"
        )
    }

    private fun initCustomView() {
        binding.reservationRegisterCustomWv.setOnBackListener(object :
            CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }

        })


        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.reservationRegisterCustomWv.backWebView()) {
                        MaterialAlertDialogBuilder(homeActivity)
                            .setMessage(getString(R.string.dialog_cancel_register_reservation))
                            .setNegativeButton(getString(R.string.dialog_register_tour_negative)) { _, _ ->

                            }
                            .setPositiveButton(getString(R.string.dialog_register_tour_positive)) { _, _ ->
                                popBackStack()
                            }.show()

                    }
                }
            })
    }

    private fun initWebView() {
        binding.reservationRegisterCustomWv.binding.customWebviewTitleWv.webViewClient =
            object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!isPageLoaded) {
                        isPageLoaded = true
                        bridge.sendUserData(
                            UserDto(
                                SharedPref.userId!!,
                                SharedPref.accessToken!!,
                                SharedPref.isNav!!,
                                SharedPref.language == "ko"
                            )
                        )
                    }
                }

            }

        val url = WEBURL + "reservation/${args.tourId}/create/${args.travId}"
        binding.reservationRegisterCustomWv.loadWebViewUrl(url)

    }

    fun showReservationRegisterFailDialog() {
        MaterialAlertDialogBuilder(homeActivity)
            .setMessage(getString(R.string.dialog_register_reservation_fail))
            .setPositiveButton(getString(R.string.dialog_confirm)) { _, _ ->

            }.show()
    }

    fun navigateToReservationRegisterDetailFragment(tourId: Int, reservationId: Int) {
        val action =
            ReservationRegisterFragmentDirections.actionReservationRegisterFragmentToReservationDetailFragment(
                reservationId = reservationId,
                tourId = tourId
            )
        navigate(action)
    }

    fun navigateToReservationRegisterChattingRoom() {
        popBackStack()
    }
}