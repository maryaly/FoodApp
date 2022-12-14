package com.umain.fooddelivery.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.umain.fooddelivery.R
import com.umain.fooddelivery.utils.Constants
import com.umain.fooddelivery.utils.Constants.DELAY_2

@AndroidEntryPoint
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        Handler().postDelayed({
            navigateToHomeFragment()
        }, DELAY_2)
    }

    private fun navigateToHomeFragment() {
        lifecycleScope.launchWhenResumed {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

}