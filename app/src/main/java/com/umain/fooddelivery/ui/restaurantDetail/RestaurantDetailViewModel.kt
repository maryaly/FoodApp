package com.umain.fooddelivery.ui.restaurantDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umain.fooddelivery.data.model.Restaurant


class RestaurantDetailViewModel : ViewModel() {

    var mStatus = MutableLiveData<String>()
    var mFilters = MutableLiveData<String>()
    var mName = MutableLiveData<String>()

    fun showInfo(model: Restaurant) {
//        model.delivery_time_minutes.let { time ->
//            mStatus.value = time.toString()
//        }
        model.filterIds.let { filters ->
            mFilters.value = filters.toString()
        }

        model.name.let { name ->
            mName.value = name
        }
    }
}