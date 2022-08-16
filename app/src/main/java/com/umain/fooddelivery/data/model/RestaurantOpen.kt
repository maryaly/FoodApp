package com.umain.fooddelivery.data.model

import com.google.gson.annotations.SerializedName

data class RestaurantOpen(

    @SerializedName("is_currently_open")
    val isCurrentlyOpen: Boolean,

    @SerializedName("restaurant_id")
    val restaurantId: String
)
