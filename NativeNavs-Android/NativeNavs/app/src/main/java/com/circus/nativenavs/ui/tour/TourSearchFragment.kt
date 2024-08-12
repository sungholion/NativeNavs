package com.circus.nativenavs.ui.tour

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.CategoryDto
import com.circus.nativenavs.data.LoginDto
import com.circus.nativenavs.databinding.FragmentTourSearchBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.CalenderDecorator
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek

class TourSearchFragment : BaseFragment<FragmentTourSearchBinding>(
    FragmentTourSearchBinding::bind,
    R.layout.fragment_tour_search
) {

    private var travelIsExpanded = true
    private var dateIsExpanded = true
    private var themeIsExpanded = true

    private lateinit var recyclerView: RecyclerView
    private lateinit var popularButtons: List<ToggleButton>

    private lateinit var homeActivity: HomeActivity

    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var categoryAdapter: TourCategoryAdapter


    private lateinit var currentList : List<CategoryDto>

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
        initAdapter()
        initSearchTravelBtn()
        initEvent()
        initCalendar(CalendarDay.today().month)
        homeActivityViewModel.apply {
            searchTravel.observe(viewLifecycleOwner) { it ->
                binding.tourSearchTravelTitleContentTv.text = it
            }
            searchDate.observe(viewLifecycleOwner) { it ->
                binding.tourSearchDateTitleContentTv.text = it
            }
        }
    }

    private fun initView() {
        binding.apply {
            if(homeActivityViewModel.searchTravel.value != ""){
                searchEditText.setText(homeActivityViewModel.searchTravel.value)
                tourSearchTravelTitleContentTv.text = homeActivityViewModel.searchTravel.value
            }
            tourSearchDateTitleContentTv.text = homeActivityViewModel.searchDate.value
        }
        binding.calendarView.setOnMonthChangedListener { widget, date ->
            // 기존에 설정되어 있던 Decorators 초기화
            binding.calendarView.removeDecorators()
            binding.calendarView.invalidateDecorators()

            // Decorators 추가
            initCalendar(date.month)
        }

        homeActivityViewModel.categoryCheckList.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it.toList())
            Log.d("reset", "initView: $it")
        }
    }

    fun initAdapter() {

        categoryAdapter = TourCategoryAdapter({ category, isChecked ->
            Log.d("a", "initAdaper: ${category.id}")
            homeActivityViewModel.toggleCategory(category.id)
        }, SharedPref.language == "ko")
        binding.tourSearchThemeRv.adapter = categoryAdapter

    }


    @SuppressLint("DefaultLocale")
    private fun initCalendar(month: Int) {
        binding.calendarView.apply {
            setHeaderTextAppearance(R.style.CalendarWidgetHeader)
            addDecorators(
                SundayDecorator(), SaturdayDecorator(),
                CalenderDecorator(requireContext()), SelectedMonthDecorator(month)
            )
            setDateTextAppearance(R.style.CalenderViewCustom)
            setWeekDayTextAppearance(R.style.CalenderViewCustom)
            setTitleFormatter { day ->
                val inputText = day.date
                val calendarHeaderElements = inputText.toString().split("-")
                val calendarHeaderBuilder = StringBuilder()

                calendarHeaderBuilder.append(calendarHeaderElements[0]).append("  ")
                    .append(calendarHeaderElements[1])

                calendarHeaderBuilder.toString()
            }
            setOnDateChangedListener { widget, date, selected ->
                val year = date.year
                val month = String.format("%02d", date.month)
                val day = String.format("%02d", date.day)
                homeActivityViewModel.updateSearchDate("$year-$month-$day")
            }
        }
    }

    private fun initEvent() {
        binding.apply {
            tourSearchBtn.setOnClickListener {
                homeActivityViewModel.let {
                    it.updateSearchTheme()
                    Log.d("img", "initEvent: ${it.searchTravel.value}")
                    Log.d("img", "initEvent: ${it.searchDate.value}")
                    Log.d("img", "initEvent: ${it.searchTheme.value}")
                    navigate(R.id.action_tourSearchFragment_to_tourListFragment)
                }
            }
            tourSearchCloseBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            tourSearchResetTv.setOnClickListener {

                homeActivityViewModel.apply {
                    resetCheck()
                    binding.apply {
                        searchEditText.setText("")
                        calendarView.clearSelection()
                    }

                    updateSearchTravel("")
                    updateSearchDate("")
                    updateCategory()
                    categoryAdapter.notifyDataSetChanged()
                }
            }
            tourSearchTravelCheckBtn.setOnClickListener {
                travelClicked()
            }
            tourSearchDateCheckBtn.setOnClickListener {
                dateClicked()
            }
            tourSearchThemeCheckBtn.setOnClickListener {
                themeClicked()
            }
            searchTravelCancelTv.setOnClickListener {
                binding.searchEditText.setText("")
            }
            searchDateCancelTv.setOnClickListener {
                binding.calendarView.clearSelection()
            }
            searchThemeCancelTv.setOnClickListener {
                homeActivityViewModel.resetCheck()
                homeActivityViewModel.updateCategory()
                categoryAdapter.notifyDataSetChanged()
            }
            buttonSeoul.setOnClickListener {
                binding.searchEditText.setText("Seoul")
            }
            buttonIncheon.setOnClickListener {
                binding.searchEditText.setText("Incheon")
            }
            buttonDaegu.setOnClickListener {
                binding.searchEditText.setText("Daegu")
            }
            buttonDaejeon.setOnClickListener {
                binding.searchEditText.setText("Daejeon")
            }
            buttonGwangju.setOnClickListener {
                binding.searchEditText.setText("Gwangju")
            }
            buttonBusan.setOnClickListener {
                binding.searchEditText.setText("Busan")
            }
            buttonJeju.setOnClickListener {
                binding.searchEditText.setText("Jeju")
            }
        }
    }

    private fun travelClicked() {
        homeActivityViewModel.updateSearchTravel(binding.searchEditText.text.toString())
        travelIsExpanded = !travelIsExpanded
        dateIsExpanded = true
        themeIsExpanded = true
        toggleExpandableLayout()
    }

    private fun dateClicked() {
        homeActivityViewModel.updateSearchTravel(binding.searchEditText.text.toString())
        dateIsExpanded = !dateIsExpanded
        travelIsExpanded = true
        themeIsExpanded = true
        toggleExpandableLayout()
    }

    private fun themeClicked() {
        homeActivityViewModel.updateSearchTravel(binding.searchEditText.text.toString())
        themeIsExpanded = !themeIsExpanded
        travelIsExpanded = true
        dateIsExpanded = true
        toggleExpandableLayout()
    }

    private fun initSearchTravelBtn() {
        popularButtons = listOf(
            binding.buttonSeoul,
            binding.buttonIncheon,
            binding.buttonDaejeon,
            binding.buttonDaegu,
            binding.buttonGwangju,
            binding.buttonBusan
        )

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
        if (themeIsExpanded) {
            binding.tourSearchThemeLl.visibility = VISIBLE
            collapse(binding.tourSearchThemeContentLayout)
        } else {
            binding.tourSearchThemeLl.visibility = GONE
            expand(binding.tourSearchThemeContentLayout)
        }
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

    /* 일요일 날짜의 색상을 설정하는 클래스 */
    private inner class SundayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val sunday = day.date.with(DayOfWeek.SUNDAY).dayOfMonth
            return sunday == day.day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object : ForegroundColorSpan(Color.RED) {})
        }
    }

    /* 토요일 날짜의 색상을 설정하는 클래스 */
    private inner class SaturdayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val saturday = day.date.with(DayOfWeek.SATURDAY).dayOfMonth
            return saturday == day.day
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object : ForegroundColorSpan(Color.BLUE) {})
        }
    }

    /* 이번달에 속하지 않지만 캘린더에 보여지는 이전달/다음달의 일부 날짜를 설정하는 클래스 */
    private inner class SelectedMonthDecorator(val selectedMonth: Int) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day.month != selectedMonth
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_d9d9
                    )
                )
            )
        }
    }

    companion object {

    }
}