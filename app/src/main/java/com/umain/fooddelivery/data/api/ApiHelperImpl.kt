package com.umain.fooddelivery.data.api


import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.RestaurantResponse
import com.umain.fooddelivery.data.model.RestaurantOpen
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val mApiService: ApiService
) : ApiHelper {

    override suspend fun getRestaurants()
            : Response<RestaurantResponse> = mApiService.getRestaurants()

    override suspend fun getFilters(
        id: String
    ): Response<Filter> = mApiService.getFilters(id = id)

    override suspend fun restaurantOpen(
        restaurantId: String
    ): Response<RestaurantOpen> = mApiService.restaurantOpen(restaurantId = restaurantId)

}