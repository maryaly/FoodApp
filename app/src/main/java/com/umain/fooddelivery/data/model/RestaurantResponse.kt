package com.umain.fooddelivery.data.model

import java.io.Serializable

data class RestaurantResponse(
    val restaurants: List<Restaurant>
) : Serializable

data class Restaurant(
    val delivery_time_minutes: Int,
    val rating: Float,
    val filterIds: List<String>,
    val id: String,
    val name: String,
    val image_url: String
) : Serializable
