package com.circus.nativenavs.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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
        homeActivityViewModel.getProfileUser(args.userId)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        homeActivityViewModel.profileUser.observe(viewLifecycleOwner) { it ->
            if (SharedPref.userId != it.id) binding.profileModifyBtn.visibility = INVISIBLE
            else binding.profileModifyBtn.visibility = VISIBLE

            binding.profileUserIv.setImageURI(it.image.toUri())
            binding.profileUserNameTv.text = it.nickname
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

            if (SharedPref.userId == it.id) {
                binding.profileReviewTitle.apply {
                    text = getString(R.string.profile_myreview)
                }
                binding.profileStampTitle.apply {
                    text = getString(R.string.profile_mystamp)
                }
            } else {
                binding.profileStampTitle.apply {
                    text = getString(R.string.profile_mystamp)
                }

                if (it.isNav) {
                    binding.profileReviewTitle.apply {
                        text = it.nickname + getString(R.string.profile_other_nav_review)
                    }
                } else {
                    binding.profileReviewTitle.apply {
                        text = it.nickname + getString(R.string.profile_other_trav_review)
                    }
                }
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

    private fun checkPassDialog() {
        val builder = AlertDialog.Builder(context)
        val view = homeActivity.layoutInflater.inflate(R.layout.dialog_pass_check, null)

        builder.setView(view)
        builder.setTitle("비밀번호 입력")
        builder.setMessage("비밀번호를 입력해주세요")
        builder.setPositiveButton("확인") { dialog, which ->
            navigate(R.id.action_profileFragment_to_profileModifylFragment)
        }

        builder.setNegativeButton("취소") { dialog, which ->
            // 취소 버튼 클릭 시 수행할 동작
        }
        builder.show()
    }

    private fun initEvent() {
        binding.profileModifyBtn.setOnClickListener {
            checkPassDialog()
        }

        binding.profileTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.profileReviewLl.setOnClickListener {
            lateinit var action: NavDirections

            homeActivityViewModel.profileUser.observe(viewLifecycleOwner) { it ->
                action = if (it.isNav) {
                    ProfileFragmentDirections.actionProfileFragmentToReviewListFragment(navId = args.userId)
                } else {
                    ProfileFragmentDirections.actionProfileFragmentToReviewListFragment(travId = args.userId)
                }
            }
            navigate(action)
        }

        binding.profileStampLl.setOnClickListener {
            navigate(R.id.action_profileFragment_to_stampFragment)
        }
    }

}