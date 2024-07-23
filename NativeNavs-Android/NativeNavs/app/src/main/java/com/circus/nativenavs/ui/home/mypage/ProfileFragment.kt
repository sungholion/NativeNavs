package com.circus.nativenavs.ui.home.mypage

import android.os.Bundle
import android.view.View
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.mypage.ProfileReviewDto
import com.circus.nativenavs.databinding.FragmentProfileBinding
import com.circus.nativenavs.ui.home.mypage.util.ProfileReviewListAdapter

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {

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
        ),ProfileReviewDto(
            4,
            "2024년1월",
            "두 번째 방문입니다. 올 때 마다 힐링하고 가요. \uD83D\uDE0A가이드님이 잘 챙겨주셨어요!",
            "res/drawable/logo_nativenavs.png",
            "아린",
            "영어"
        )
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileReviewRv.apply {
            this.adapter = ProfileReviewListAdapter().apply {
                submitList(dummy)
            }
        }


    }

}