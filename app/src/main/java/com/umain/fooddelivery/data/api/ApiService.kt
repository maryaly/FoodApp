package com.umain.fooddelivery.data.api


import com.umain.fooddelivery.data.model.CollectionModel
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.RestaurantOpen
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("collections/{apiKey}")
    suspend fun getCollection(
        @Path("apiKey") apiKey: String
    ): Response<CollectionModel>

    @GET("filter/{id}")
    suspend fun getFilters(
        @Path("id") id: String
    ): Response<Filter>

    @GET("open/{restaurant_id}")
    suspend fun restaurantOpen(
        @Path("restaurant_id") restaurantId: String
    ): Response<RestaurantOpen>

}
