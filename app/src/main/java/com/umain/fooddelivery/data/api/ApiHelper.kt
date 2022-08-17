package com.umain.fooddelivery.data.api

import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.RestaurantOpen
import com.umain.fooddelivery.data.model.RestaurantResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getRestaurants(): RestaurantResponse

    suspend fun getFilters(
        id: String
    ): Response<Filter>

    suspend fun restaurantOpen(
        restaurantId: String
    ): Response<RestaurantOpen>
}