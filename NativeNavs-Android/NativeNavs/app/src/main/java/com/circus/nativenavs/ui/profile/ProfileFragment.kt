package com.circus.nativenavs.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.ProfileReviewDto
import com.circus.nativenavs.data.Review
import com.circus.nativenavs.databinding.FragmentProfileBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "ProfileFragment"

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {
    private var reviewList = mutableListOf<ProfileReviewDto>()
    private lateinit var profileReviewAdapter : ProfileReviewListAdapter
    private var dummy = arrayListOf<ProfileReviewDto>(
        ProfileReviewDto(
            4,
            "2024년1월",
            "무플 방지 위원회에서 나왔습니다.. \uD83D\uDE0A 리뷰를 작성해보세요!",
            "res/drawable/logo_nativenavs.png",
            "도움이",
            "영어",
            ""
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
        initObserve()
    }

    private fun initData() {
        homeActivityViewModel.let {
            it.getProfileUser(args.userId)
            if (args.userId == SharedPref.userId) {
                if (SharedPref.isNav == true) it.getNavReview(args.userId)
                else it.getTravReview(args.userId)
            } else {
                if (args.navId != 0) it.getNavReview(args.navId)
                else it.getTravReview(args.travId)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        homeActivityViewModel.profileUser.observe(viewLifecycleOwner) { it ->
            if (SharedPref.userId != it.id) binding.profileModifyBtn.visibility = INVISIBLE
            else binding.profileModifyBtn.visibility = VISIBLE
            Glide.with(this)
                .load(it.image) // 불러올 이미지 url
                .placeholder(R.drawable.logo_nativenavs) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(R.drawable.logo_nativenavs) // 로딩 에러 발생 시 표시할 이미지
                .fallback(R.drawable.logo_nativenavs) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(binding.profileUserIv) // 이미지를 넣을 뷰
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
        profileReviewAdapter = ProfileReviewListAdapter()
        binding.profileReviewRv.apply {
            this.adapter = profileReviewAdapter.apply {
                submitList(dummy)
            }
        }
    }

    private fun checkPassDialog() {
        val builder = MaterialAlertDialogBuilder(homeActivity)
        val view = homeActivity.layoutInflater.inflate(R.layout.dialog_pass_check, null)

        builder.setView(view)
        builder.setTitle(getString(R.string.dialog_pass_title))
        builder.setMessage(getString(R.string.dialog_pass_content))
        builder.setPositiveButton(getString(R.string.dialog_ok_btn)) { dialog, which ->
            navigate(R.id.action_profileFragment_to_profileModifylFragment)
        }

        builder.setNegativeButton(getString(R.string.dialog_cancel_btn)) { dialog, which ->
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

    private fun initObserve() {
        homeActivityViewModel.apply {
            reviewStatus.observe(viewLifecycleOwner) {
                if (it != -1) {
                    this.profileUserReviewDto.value?.reviews?.let { review ->
                        reviewList.removeAll(reviewList)
                        review.map { it }.take(3).forEach { dto ->
                            reviewList.add(ProfileReviewDto(
                                dto.score.toInt(),
                                formatDate(dto.createdAt.toString()),
                                dto.description,
                                dto.imageUrls[0],
                                dto.reviewer.nickname,
                                dto.reviewer.userLanguage,
                                dto.reviewer.image
                            ))

                        }

                        if(reviewList.size == 0 ) profileReviewAdapter.submitList(dummy)
                        else profileReviewAdapter.submitList(reviewList)
                    }


                }
            }
        }
    }
    fun formatDate(inputDate: String): String {
        // 기존 날짜 문자열 포맷 정의
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        // 새로운 날짜 문자열 포맷 정의
        val outputFormatter =
            if(SharedPref.language == "ko") DateTimeFormatter.ofPattern("yyyy년 M월")
            else DateTimeFormatter.ofPattern("MMMM yyyy");

        // 날짜 문자열을 LocalDateTime 객체로 파싱
        val dateTime = LocalDateTime.parse(inputDate, inputFormatter)

        // LocalDateTime 객체를 원하는 포맷의 문자열로 변환
        return dateTime.format(outputFormatter)
    }
}