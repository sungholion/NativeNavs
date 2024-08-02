package com.circus.nativenavs.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.circus.nativenavs.R
import com.circus.nativenavs.databinding.LayoutTitleBackBinding

class CustomTiltleBackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val binding = LayoutTitleBackBinding.inflate(LayoutInflater.from(context), this, true)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleBackView)
        val titleText = attributes.getString(R.styleable.TitleBackView_titleText)
        binding.titleText = titleText
        attributes.recycle()
    }
}