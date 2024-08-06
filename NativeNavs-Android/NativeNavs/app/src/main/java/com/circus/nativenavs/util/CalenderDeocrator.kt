package com.circus.nativenavs.util

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.circus.nativenavs.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.DayOfWeek

class CalenderDecorator(context: Context) : DayViewDecorator {
    private val drawable = ContextCompat.getDrawable(context, R.drawable.stroke_round_circle_gray_d9d9)
    private var date = CalendarDay.today()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }
    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable!!)
    }
}

/* 선택된 날짜의 background를 설정하는 클래스 */
class DayDecorator(context: Context) : DayViewDecorator {
    private val drawable = ContextCompat.getDrawable(context,R.drawable.stroke_round_circle_gray_d9d9)
    // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable!!)
    }
}

