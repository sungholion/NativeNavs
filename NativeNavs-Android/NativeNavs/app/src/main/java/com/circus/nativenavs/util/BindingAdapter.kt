package com.circus.nativenavs.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.circus.nativenavs.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("imgUrl")
fun ImageView.loadImageUrl(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl) // 불러올 이미지 url
        .placeholder(R.drawable.logo_nativenavs) // 이미지 로딩 시작하기 전 표시할 이미지
        .error(R.drawable.logo_nativenavs) // 로딩 에러 발생 시 표시할 이미지
        .fallback(R.drawable.logo_nativenavs) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
        .into(this) // 이미지를 넣을 뷰
    this.adjustViewBounds = true
}

@BindingAdapter("time")
fun TextView.setTimeText(time: LocalDateTime) {
    this.text = time.format(DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분"))
}
