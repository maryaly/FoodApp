package com.umain.fooddelivery.data.repository

import com.umain.fooddelivery.data.api.ApiHelper
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.RestaurantOpen
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val mApiHelper: ApiHelper
) {

    suspend fun getRestaurants() = mApiHelper.getRestaurants()

    suspend fun getFilters(
        id: String
    ): Response<Filter> = mApiHelper.getFilters(id = id)

    suspend fun restaurantOpen(
        restaurantId: String
    ): Response<RestaurantOpen> = mApiHelper.restaurantOpen(restaurantId = restaurantId)
}