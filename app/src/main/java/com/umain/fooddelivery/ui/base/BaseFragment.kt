package com.umain.fooddelivery.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected abstract fun setupView()
    protected abstract fun setupUiListener()
    protected abstract fun setupObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupUiListener()
        setupObservers()
    }
}