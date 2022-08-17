package com.umain.fooddelivery.data.repository

import com.umain.fooddelivery.data.api.ApiHelper
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.RestaurantOpen
import com.umain.fooddelivery.data.model.RestaurantResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val mApiHelper: ApiHelper
) {

    fun getRestaurants(): Flow<RestaurantResponse> = flow {
        val response= mApiHelper.getRestaurants()
        emit(response)
    }.flowOn(Dispatchers.IO)

    suspend fun getFilters(
        id: String
    ): Response<Filter> = mApiHelper.getFilters(id = id)

    suspend fun restaurantOpen(
        restaurantId: String
    ): Response<RestaurantOpen> = mApiHelper.restaurantOpen(restaurantId = restaurantId)
}