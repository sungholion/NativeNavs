package com.circus.nativenavs.ui.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavHost
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {

    private val activityViewModel: SignUpActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.signup_fragment) as NavHost
        val navController = navHostFragment.navController

        activityViewModel.updateLanguageList()
    }

}