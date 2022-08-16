package com.umain.fooddelivery.data.model

import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("image_url")
    val imageURL: String,
    val name: String,
    val id: String
)
