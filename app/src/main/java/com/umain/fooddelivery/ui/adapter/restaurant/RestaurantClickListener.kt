package com.umain.fooddelivery.ui.adapter.restaurant

import com.umain.fooddelivery.data.model.Restaurant

interface RestaurantClickListener {
    fun OnListenerClicked(restaurant: Restaurant)
}