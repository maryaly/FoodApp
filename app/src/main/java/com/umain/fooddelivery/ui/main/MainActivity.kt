package com.umain.fooddelivery.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.umain.fooddelivery.R
import com.umain.fooddelivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

    }

    fun showProgress() {
        mBinding.mainActivityLoading.visibility = View.VISIBLE
    }

    fun hideProgress() {
        mBinding.mainActivityLoading.visibility = View.INVISIBLE
    }


}
