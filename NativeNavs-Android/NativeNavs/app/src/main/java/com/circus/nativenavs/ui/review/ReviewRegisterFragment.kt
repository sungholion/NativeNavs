package com.circus.nativenavs.ui.review

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
import com.circus.nativenavs.databinding.FragmentReviewRegisterBinding
import com.circus.nativenavs.util.CustomTitleWebView
import com.circus.nativenavs.util.popBackStack
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.WEBURL
import com.circus.nativenavs.util.navigate
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "ReviewRegisterFragment"

class ReviewRegisterFragment : BaseFragment<FragmentReviewRegisterBinding>(
    FragmentReviewRegisterBinding::bind,
    R.layout.fragment_review_register
) {

    private lateinit var homeActivity: HomeActivity
    private val args: ReviewRegisterFragmentArgs by navArgs()

    private lateinit var bridge: ReviewRegisterBridge
    private var isPageLoaded = false

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

    private fun initBridge() {
        bridge = ReviewRegisterBridge(
            homeActivity,
            this,
            binding.reviewRegisterCustomWv.binding.customWebviewTitleWv
        )
        binding.reviewRegisterCustomWv.binding.customWebviewTitleWv.addJavascriptInterface(
            bridge,
            "Android"
        )
    }

    private fun initCustomView() {
        binding.reviewRegisterCustomWv.setOnBackListener(object :
            CustomTitleWebView.OnBackClickListener {
            override fun onClick() {
                popBackStack()
            }
        })

        homeActivity.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!binding.reviewRegisterCustomWv.backWebView()) {
                        MaterialAlertDialogBuilder(homeActivity)
                            .setMessage(getString(R.string.dialog_cancel_register_review))
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
        binding.reviewRegisterCustomWv.binding.customWebviewTitleWv.webViewClient =
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

        binding.reviewRegisterCustomWv.binding.customWebviewTitleWv.webChromeClient =
            object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@ReviewRegisterFragment.filePathCallback = filePathCallback

                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    fileChooserLauncher.launch(intent)
                    return true
                }
            }

        val url = WEBURL + "tour/detail/${args.tourId}/reviews/create/${args.reservationId}"
        Log.d(TAG, "initCustomView: $url")
        binding.reviewRegisterCustomWv.loadWebViewUrl(url)

    }

    fun showReviewRegisterFailDialog() {
        MaterialAlertDialogBuilder(homeActivity)
            .setMessage(getString(R.string.dialog_register_review_fail))
            .setPositiveButton(getString(R.string.dialog_confirm)) { _, _ ->

            }.show()
    }

    fun moveFromReviewRegisterToReviewListFragment(tourId: Int) {
        val action =
            ReviewRegisterFragmentDirections.actionReviewRegisterFragmentToReviewListFragment(
                tourId = tourId
            )
        navigate(action)
    }


}