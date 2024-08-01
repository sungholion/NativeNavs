package com.circus.nativenavs.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.ProfileReviewDto
import com.circus.nativenavs.databinding.FragmentProfileBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {

    private val dummy = arrayListOf<ProfileReviewDto>(
        ProfileReviewDto(
            4,
            "2024년1월",
            "두 번째 방문입니다. 올 때 마다 힐링하고 가요. \uD83D\uDE0A가이드님이 잘 챙겨주셨어요!",
            "res/drawable/logo_nativenavs.png",
            "아린",
            "영어"
        ),
        ProfileReviewDto(
            4,
            "2024년1월",
            "두 번째 방문입니다. 올 때 마다 힐링하고 가요. \uD83D\uDE0A가이드님이 잘 챙겨주셨어요!",
            "res/drawable/logo_nativenavs.png",
            "아린",
            "영어"
        ), ProfileReviewDto(
            4,
            "2024년1월",
            "두 번째 방문입니다. 올 때 마다 힐링하고 가요. \uD83D\uDE0A가이드님이 잘 챙겨주셨어요!",
            "R.drawable.profile_review_sample",
            "아린",
            "영어"
        )
    )

    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var homeActivity: HomeActivity
    private val args: ProfileFragmentArgs by navArgs()

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

        initData()
        initView()
        initAdapter()
        initEvent()


    }

    private fun initData() {
        homeActivityViewModel.getProfileUser(SharedPref.userId ?: 10)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        homeActivityViewModel.profileUser.observe(viewLifecycleOwner) { it ->
            if (SharedPref.userId != it.id) binding.profileModifyBtn.visibility = INVISIBLE
            else binding.profileModifyBtn.visibility = VISIBLE

            binding.profileUserIv.setImageURI(it.image.toUri())
            binding.profileUserNameTv.text = it.name
            binding.profileUserType.text =
                if (it.isNav) getString(R.string.sign_type_nav) else getString(R.string.sign_type_trav)
            binding.profileUserNation.apply {
                text = getString(R.string.profile_nation) + " " + it.nation
            }
            binding.profileUserLanguage.apply {
                text = getString(R.string.profile_user_language) + " " + it.userLanguage
            }
            binding.profileUserUseNum.apply {
                text = getString(R.string.profile_user_use) + " " + it.travReservationCount
            }
        }
    }

    private fun initAdapter() {
        binding.profileReviewRv.apply {
            this.adapter = ProfileReviewListAdapter().apply {
                submitList(dummy)
            }
        }
    }

    private fun initEvent() {
        binding.profileModifyBtn.setOnClickListener {
            navigate(R.id.action_profileFragment_to_profileModifylFragment)
        }

        binding.profileTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.profileReviewTitle.setOnClickListener {
            lateinit var action: NavDirections
            if(args.userId == -1){
                if(SharedPref.isNav!!){
                     action = ProfileFragmentDirections.actionProfileFragmentToReviewListFragment()
                }else{

                }
            }else{

            }
            //만약 nav이면
//            val action = ProfileFragmentDirections.actionProfileFragmentToReviewListFragment(navId = args.userId)
            //만약 trav이면
//            val action = ProfileFragmentDirections.actionProfileFragmentToReviewListFragment(travId = args.userId)

            navigate(action)
        }

        binding.profileStampTitle.setOnClickListener {
            navigate(R.id.action_profileFragment_to_stampFragment)
        }
    }

}