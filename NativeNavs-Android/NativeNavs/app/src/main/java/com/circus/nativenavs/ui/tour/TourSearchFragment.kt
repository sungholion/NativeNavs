package com.circus.nativenavs.ui.tour

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ToggleButton
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentTourSearchBinding

class TourSearchFragment : BaseFragment<FragmentTourSearchBinding>(FragmentTourSearchBinding::bind,R.layout.fragment_tour_search) {

    private var travelIsExpanded = true
    private var dateIsExpanded = true
    private var themeIsExpanded = true


    private lateinit var popularButtons: List<ToggleButton>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initSearchTravelBtn()
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
            travelIsExpanded = !travelIsExpanded
            dateIsExpanded = true
            themeIsExpanded = true
            toggleExpandableLayout()
        }
        binding.tourSearchDateLl.setOnClickListener {
            dateIsExpanded = !dateIsExpanded
            travelIsExpanded = true
            themeIsExpanded = true
            toggleExpandableLayout()
        }

        binding.tourSearchThemeLl.setOnClickListener {
            themeIsExpanded = !themeIsExpanded
            travelIsExpanded = true
            dateIsExpanded = true
            toggleExpandableLayout()
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
            binding.tourSearchTravelLl.visibility = VISIBLE
            collapse(binding.tourSearchTravelContentLayout)
        } else {
            binding.tourSearchTravelLl.visibility = GONE
            expand(binding.tourSearchTravelContentLayout)
        }
        // 날짜 검색창
        if (dateIsExpanded) {
            binding.tourSearchDateLl.visibility = VISIBLE
            collapse(binding.tourSearchDateContentLayoutLl)
        } else {
            binding.tourSearchDateLl.visibility = GONE
            expand(binding.tourSearchDateContentLayoutLl)
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