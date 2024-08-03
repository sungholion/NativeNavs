package com.circus.nativenavs.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.popBackStack() {
    this.findNavController().popBackStack()
    KeyBoardUtil.hide(requireActivity())
}

fun Fragment.navigate(action: Int) {
    this.findNavController().navigate(action)
    KeyBoardUtil.hide(requireActivity())
}

fun Fragment.navigate(actionWithData: NavDirections) {
    this.findNavController().navigate(actionWithData)
    KeyBoardUtil.hide(requireActivity())
}