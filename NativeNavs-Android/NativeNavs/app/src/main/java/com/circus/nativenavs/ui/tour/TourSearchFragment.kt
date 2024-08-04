package com.circus.nativenavs.ui.tour

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourSearchBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TourSearchFragment : BaseFragment<FragmentTourSearchBinding>(FragmentTourSearchBinding::bind,R.layout.fragment_tour_search) {

    private var travelIsExpanded = true
    private var dateIsExpanded = true
    private var themeIsExpanded = true


    private lateinit var popularButtons: List<ToggleButton>

    private lateinit var homeActivity: HomeActivity

    private val homeActivityViewModel : HomeActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initView()
        initSearchTravelBtn()
        initEvent()

        homeActivityViewModel.searchTravel.observe(viewLifecycleOwner){ it->
            binding.tourSearchTravelTitleContentTv.text = it
        }

    }
    private fun initView(){
        binding.apply {
            tourSearchTravelTitleContentTv.text = homeActivityViewModel.searchTravel.value
            tourSearchDateTitleContentTv.text = homeActivityViewModel.searchDate.value
        }
    }
    private fun initEvent() {
        binding.apply {
            tourSearchBtn.setOnClickListener {
                navigate(R.id.action_tourSearchFragment_to_tourListFragment)
            }
            tourSearchCloseBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            tourSearchResetTv.setOnClickListener {
                homeActivityViewModel.updateSearchTravel("")
                homeActivityViewModel.updateSearchDate("")
            }

            buttonSeoul.setOnClickListener {
                binding.searchEditText.setText("서울")
                travelClicked()
            }
            buttonIncheon.setOnClickListener {
                binding.searchEditText.setText("인천")
                travelClicked()
            }
            buttonDaegu.setOnClickListener {
                binding.searchEditText.setText("대구")
                travelClicked()
            }
            buttonDaejeon.setOnClickListener {
                binding.searchEditText.setText("대전")
                travelClicked()
            }
            buttonGwangju.setOnClickListener {
                binding.searchEditText.setText("광주")
                travelClicked()
            }
            buttonBusan.setOnClickListener {
                binding.searchEditText.setText("부산")
                travelClicked()
            }
            buttonJeju.setOnClickListener {
                binding.searchEditText.setText("제주")
                travelClicked()
            }
        }
    }

    private fun travelClicked(){
        homeActivityViewModel.updateSearchTravel(binding.searchEditText.text.toString())
        travelIsExpanded = !travelIsExpanded
        dateIsExpanded = true
        themeIsExpanded = true
        toggleExpandableLayout()
    }

    private fun dateClicked(){
        homeActivityViewModel.updateSearchTravel(binding.searchEditText.text.toString())
        dateIsExpanded = !dateIsExpanded
        travelIsExpanded = true
        themeIsExpanded = true
        toggleExpandableLayout()
    }

    private fun themeClicked(){
        homeActivityViewModel.updateSearchTravel(binding.searchEditText.text.toString())
        themeIsExpanded = !themeIsExpanded
        travelIsExpanded = true
        dateIsExpanded = true
        toggleExpandableLayout()
    }

    private fun initSearchTravelBtn(){
        popularButtons = listOf(
            binding.buttonSeoul,
            binding.buttonIncheon,
            binding.buttonDaejeon,
            binding.buttonDaegu,
            binding.buttonGwangju,
            binding.buttonBusan)

        binding.tourSearchTravelLl.setOnClickListener {
            travelClicked()
        }
        binding.tourSearchDateLl.setOnClickListener {
            dateClicked()
        }

        binding.tourSearchThemeLl.setOnClickListener {
            themeClicked()
        }

        popularButtons.forEach { button ->
            button.setOnClickListener {
                handleToggleButton(button)
            }
        }
    }

    private fun toggleExpandableLayout() {
            // 여행 검색창
            if (travelIsExpanded) {
                CoroutineScope(Dispatchers.Main).launch {
                    collapse(binding.tourSearchTravelContentLayout)
                    delay(200)
                    binding.tourSearchTravelLl.visibility = VISIBLE
                }
            } else {
                binding.tourSearchTravelLl.visibility = GONE
                expand(binding.tourSearchTravelContentLayout)
            }
            // 날짜 검색창
            if (dateIsExpanded) {
                binding.tourSearchDateLl.visibility = VISIBLE
                collapse(binding.tourSearchDateContentLayout)
            } else {
                binding.tourSearchDateLl.visibility = GONE
                expand(binding.tourSearchDateContentLayout)
            }

        // 테마 검색창
    }

    private fun expand(view: View) {
        view.visibility = View.VISIBLE
        val height = getContentHeight(view)
        val animator = ValueAnimator.ofInt(0, height)
        animator.addUpdateListener { valueAnimator ->
            view.layoutParams.height = valueAnimator.animatedValue as Int
            view.requestLayout()
        }
        animator.duration = 300
        animator.start()
    }

    private fun collapse(view: View) {
        val initialHeight = view.height
        val animator = ValueAnimator.ofInt(initialHeight, 0)
        animator.addUpdateListener { valueAnimator ->
            view.layoutParams.height = valueAnimator.animatedValue as Int
            view.requestLayout()
        }
        animator.duration = 300
        animator.start()
        animator.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animator: android.animation.Animator) {}
            override fun onAnimationEnd(animator: android.animation.Animator) {
                view.visibility = View.GONE
            }
            override fun onAnimationCancel(animator: android.animation.Animator) {}
            override fun onAnimationRepeat(animator: android.animation.Animator) {}
        })
    }

    private fun getContentHeight(view: View): Int {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        return view.measuredHeight
    }

    private fun handleToggleButton(selectedButton: ToggleButton) {
        popularButtons.forEach { button ->
            button.isChecked = button == selectedButton
        }
    }
}