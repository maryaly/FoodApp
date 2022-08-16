package com.umain.fooddelivery.data.api

import com.umain.fooddelivery.data.model.CollectionModel
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.RestaurantOpen
import retrofit2.Response

interface ApiHelper {

    suspend fun getCollection(
        apiKey: String
    ): Response<CollectionModel>

    suspend fun getFilters(
        id: String
    ): Response<Filter>

    suspend fun restaurantOpen(
        restaurantId: String
    ): Response<RestaurantOpen>
}