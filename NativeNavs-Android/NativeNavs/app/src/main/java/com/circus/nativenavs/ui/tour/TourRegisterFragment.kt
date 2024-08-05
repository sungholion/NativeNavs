package com.circus.nativenavs.ui.tour

import android.app.Activity
import android.app.Dialog
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourRegisterBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.popBackStack
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "TourRegisterFragment"

class TourRegisterFragment : BaseFragment<FragmentTourRegisterBinding>(
    FragmentTourRegisterBinding::bind,
    R.layout.fragment_tour_register
) {

    private lateinit var homeActivity: HomeActivity
    private var isPageLoaded = false

    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var fileChooserLauncher: ActivityResultLauncher<Intent>


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

        initCustomView()
        initWebView()
        initFileChooserLauncher()

    }

    private fun initFileChooserLauncher() {
        fileChooserLauncher =
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
    }

    private fun initCustomView() {
        binding.tourRegisterWv.setOnBackListener(object : CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.tourRegisterWv.backWebView()) {
                        MaterialAlertDialogBuilder(homeActivity)
                            .setMessage(getString(R.string.dialog_cancel_register_tour))
                            .setNegativeButton(getString(R.string.dialog_register_tour_negative)) { dialog, which ->

                            }
                            .setPositiveButton(getString(R.string.dialog_register_tour_positive)) { dialog, which ->
                                popBackStack()
                            }.show()

                    }
                }
            })

    }

    private fun initWebView() {
        binding.tourRegisterWv.binding.customWebviewTitleWv.webViewClient =
            object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!isPageLoaded) {
                        isPageLoaded = true
//                    bridge.sendUserData(UserDto(1, "use token", true))
                    }
                }

            }

        binding.tourRegisterWv.binding.customWebviewTitleWv.webChromeClient =
            object : WebChromeClient(){
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@TourRegisterFragment.filePathCallback = filePathCallback

                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    fileChooserLauncher.launch(intent)
                    return true
                }
            }

        val url = "https://i11d110.p.ssafy.io/tour/create"
        Log.d(TAG, "initCustomView: $url")
        binding.tourRegisterWv.loadWebViewUrl(url)

    }

}