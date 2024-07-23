package com.circus.nativenavs.ui.signup

import android.os.Bundle
import androidx.navigation.NavHost
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.signup_fragment) as NavHost
        val navController = navHostFragment.navController
    }

}