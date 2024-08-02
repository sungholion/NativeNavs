package com.circus.nativenavs.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("imgUrl")
fun ImageView.loadImageUrl(imgUrl: String) {

}

@BindingAdapter("time")
fun TextView.setTimeText(time: Long) {
    this.text = time.toString()
}
