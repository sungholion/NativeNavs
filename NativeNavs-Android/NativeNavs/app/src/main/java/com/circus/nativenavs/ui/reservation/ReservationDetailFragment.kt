package com.circus.nativenavs.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentReservationDetailBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.qr.CustomCaptureActivity
import com.circus.nativenavs.ui.qr.QRCreateActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import com.google.zxing.integration.android.IntentIntegrator

class ReservationDetailFragment : BaseFragment<FragmentReservationDetailBinding>(
    FragmentReservationDetailBinding::bind,
    R.layout.fragment_reservation_detail
) {

    private lateinit var homeActivity: HomeActivity
    private lateinit var bridge: ReservationDetailBridge
    private var isPageLoaded = false
    private val args: ReservationDetailFragmentArgs by navArgs()
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
        initCustomWebView()

    }

    private fun initBridge() {
        bridge =
            ReservationDetailBridge(
                homeActivity,
                this,
                binding.reservationDetailCustomWv.binding.customWebviewTitleWv
            )
        binding.reservationDetailCustomWv.binding.customWebviewTitleWv.addJavascriptInterface(
            bridge,
            "Android"
        )
    }

    private fun initWebView() {

        binding.reservationDetailCustomWv.binding.customWebviewTitleWv.webViewClient =
            object : WebViewClient() {
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

        val url = WEBURL + "reservation/${args.tourId}/detail/${args.reservationId}"
        Log.d(TAG, "initCustomView: $url")
        binding.reservationDetailCustomWv.loadWebViewUrl(url)

    }

    private fun initCustomWebView() {
        binding.reservationDetailCustomWv.setOnBackListener(object :
            CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }

        })
        binding.reservationDetailCustomWv.setOnQRClickListener(
            object : CustomTitleWebView.OnQRClickListener {
            override fun onClick() {
                if (SharedPref.isNav == true) {
                    startQRCodeScan()
                }
                else {
                    startActivity(Intent(requireContext(), QRCreateActivity::class.java).apply {
                        action = "${args.reservationId}"
                    })
                }
            }
        })
        binding.reservationDetailCustomWv.binding.customWebviewTitleQrIv.visibility = VISIBLE
        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.reservationDetailCustomWv.backWebView()) {
                        popBackStack()
                    }
                }

            })
    }

    fun navigateToReservationDetailChattingRoom(chatId: Int) {
        val action =
            ReservationDetailFragmentDirections.actionReservationDetailFragmentToChattingRoomFragment(
                chatId = chatId
            )
        navigate(action)
    }
    fun navigateBack(){
        popBackStack()
    }



    private fun startQRCodeScan() {
        IntentIntegrator.forSupportFragment(this).apply {
            setOrientationLocked(true)
            setPrompt("Scan a QR Code")
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setCaptureActivity(CustomCaptureActivity::class.java)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        intentResult?.contents?.let {
            // Handle the scanned result here
            showToast(it)
        }
    }

}