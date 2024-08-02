package com.circus.nativenavs.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentProfileModifyBinding
import com.circus.nativenavs.ui.home.HomeActivity

class ProfileModifylFragment : BaseFragment<FragmentProfileModifyBinding>(FragmentProfileModifyBinding::bind,R.layout.fragment_profile_modify) {

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()


    }
    fun initView(){

    }
    fun initEvent(){
        binding.profileModifyCompleteBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.profileModifyTitle.customWebviewTitleBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}