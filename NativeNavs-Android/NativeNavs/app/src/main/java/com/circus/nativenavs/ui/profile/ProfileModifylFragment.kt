package com.circus.nativenavs.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentProfileModifyBinding

class ProfileModifylFragment : BaseFragment<FragmentProfileModifyBinding>(FragmentProfileModifyBinding::bind,R.layout.fragment_profile_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileModifyCompleteBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.profileModifyTitle.customWebviewTitleBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}