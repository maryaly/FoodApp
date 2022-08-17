package com.umain.fooddelivery.ui.restaurantDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber


class RestaurantDetailViewModel : ViewModel() {

    var mStatus = MutableLiveData<String>()
    var mFilters = MutableLiveData<String>()
    var mName = MutableLiveData<String>()

    fun showInfo(mArgs: RestaurantDetailFragmentArgs) {
        Timber.d("IsOpen -> $mArgs")

        if (mArgs.restaurantOpen.isCurrentlyOpen)
            mStatus.value = "Open"
        else
            mStatus.value = "Close"

        mArgs.restaurant.filterIds.let { filters ->
            mFilters.value = filters.toString()
        }

        mArgs.restaurant.name.let { name ->
            mName.value = name
        }
    }
}