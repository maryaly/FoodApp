package com.umain.fooddelivery.ui.adapter.restaurant

import com.umain.fooddelivery.data.model.Restaurant

interface ClickListener {
    fun OnListenerClicked(restaurant: Restaurant)
}