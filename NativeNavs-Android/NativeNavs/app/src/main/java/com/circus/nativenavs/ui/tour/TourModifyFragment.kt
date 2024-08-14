package com.circus.nativenavs.ui.tour

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.UserDto
import com.circus.nativenavs.databinding.FragmentTourModifyBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.popBackStack
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "TourModifyFragment"

class TourModifyFragment : BaseFragment<FragmentTourModifyBinding>(
    FragmentTourModifyBinding::bind,
    R.layout.fragment_tour_modify
) {

    private lateinit var homeActivity: HomeActivity
    private val args: TourModifyFragmentArgs by navArgs()

    private lateinit var bridge: TourModifyBridge
    private var isPageLoaded = false


    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
        isPageLoaded = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBridge()
        initCustomView()
        initWebView()

    }


    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (filePathCallback != null) {
                    filePathCallback?.onReceiveValue(arrayOf(uri!!))
                    filePathCallback = null
                }
            } else {
                if (filePathCallback != null) {
                    filePathCallback?.onReceiveValue(null)
                    filePathCallback = null
                }
            }
        }

    private fun initWebView() {
        binding.tourModifyWv.binding.customWebviewTitleWv.webViewClient = object : WebViewClient() {
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
        binding.tourModifyWv.binding.customWebviewTitleWv.webChromeClient =
            object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@TourModifyFragment.filePathCallback = filePathCallback

                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    fileChooserLauncher.launch(intent)
                    return true
                }
            }

        val url = WEBURL + "tour/edit/${args.tourId}"
        binding.tourModifyWv.loadWebViewUrl(url)

    }

    private fun initBridge() {
        bridge =
            TourModifyBridge(homeActivity, this, binding.tourModifyWv.binding.customWebviewTitleWv)
        binding.tourModifyWv.binding.customWebviewTitleWv.addJavascriptInterface(bridge, "Android")
    }

    private fun initCustomView() {
        binding.tourModifyWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.tourModifyWv.backWebView()) {
                        MaterialAlertDialogBuilder(homeActivity)
                            .setMessage(getString(R.string.dialog_cancel_modify_tour))
                            .setNegativeButton(getString(R.string.dialog_register_tour_negative)) { _, _ ->

                            }
                            .setPositiveButton(getString(R.string.dialog_register_tour_positive)) { _, _ ->
                                popBackStack()
                            }.show()

                    }
                }
            })

    }

    fun navigateFromTourModifyToTourDetailFragment(tourId: Int, navId: Int) {
        popBackStack()
    }

    fun showModifyFailDialog() {
        MaterialAlertDialogBuilder(homeActivity)
            .setMessage(getString(R.string.dialog_modify_fail))
            .setPositiveButton(getString(R.string.dialog_confirm)) { _, _ ->

            }.show()
    }
}