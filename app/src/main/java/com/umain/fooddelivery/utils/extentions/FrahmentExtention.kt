package com.umain.fooddelivery.utils.extentions

import androidx.fragment.app.Fragment
import com.umain.fooddelivery.ui.main.MainActivity


fun Fragment.showProgress() {
    activity?.let {
        (it as MainActivity).showProgress()
    }
}

fun Fragment.hideProgress() {
    activity?.let {
        (it as MainActivity).hideProgress()
    }
}