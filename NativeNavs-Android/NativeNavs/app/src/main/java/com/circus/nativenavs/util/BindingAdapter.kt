package com.circus.nativenavs.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.circus.nativenavs.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("imgUrl")
fun ImageView.loadImageUrl(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .fitCenter()
        .transform(CenterCrop(), RoundedCorners(10))
        .placeholder(R.drawable.logo_nativenavs)
        .error(R.drawable.logo_nativenavs)
        .fallback(R.drawable.logo_nativenavs)
        .into(this)
    this.adjustViewBounds = true
}

@BindingAdapter("time")
fun TextView.setTimeText(time: String) {
    val localDateTime = LocalDateTime.parse(time)
    val formattedTime = localDateTime.format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))
    this.text = formattedTime
}
