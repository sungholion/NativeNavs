package com.circus.nativenavs.ui.home.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentMypageBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.ui.login.LoginActivity
import com.circus.nativenavs.util.LOGOUT
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate

class MypageFragment :
    BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::bind, R.layout.fragment_mypage) {

    private lateinit var homeActivity: HomeActivity
    private val homeActivityViewModel : HomeActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()

        homeActivityViewModel.withdrawalStatus.observe(viewLifecycleOwner){ statusCode ->
            when(statusCode){
                200 -> {
                    showToast("회원 탈퇴 성공")
                    SharedPref.remove(LOGOUT)
                    startActivity(Intent(requireContext(),LoginActivity::class.java))
                    activity?.finish()
                }
                else ->{
                    showToast("회원 탈퇴 실패")
                }
            }


        }
    }

    private fun initEvent() {
        binding.mypageNotiIv.setOnClickListener {
            navigate(R.id.action_mypageFragment_to_notificationFragment)
        }

        binding.mypageProfileCl.setOnClickListener {
            navigate(R.id.action_mypageFragment_to_profileFragment)
        }

        binding.mypageSettingCl.setOnClickListener {
            navigate(R.id.action_mypageFragment_to_appSettingFragment)
        }

        binding.mypageTeamCl.setOnClickListener {
            navigate(R.id.action_mypageFragment_to_teamIntroFragment)
        }

        binding.mypageTosCl.setOnClickListener {
            navigate(R.id.action_mypageFragment_to_tosFragment)
        }

        binding.mypageLogoutCl.setOnClickListener {
            SharedPref.remove(LOGOUT)
            startActivity(Intent(requireContext(),LoginActivity::class.java))
            activity?.finish()
        }

        binding.mypageWithdrawalCl.setOnClickListener {
            homeActivityViewModel.withdrawal()
        }
    }

}