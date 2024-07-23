package com.circus.nativenavs.ui.home.mypage

import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentMypageBinding
import com.circus.nativenavs.util.navigate

class MypageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::bind, R.layout.fragment_mypage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profile.setOnClickListener {
            navigate(R.id.action_mypageFragment_to_profileFragment)
        }
    }

}