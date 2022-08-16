package com.umain.fooddelivery.data.model

import com.google.gson.annotations.SerializedName

data class CollectionModel(
    val info: Info,
    val item: List<Item>,
    val variable: List<Variable>
)

data class Info(
    @SerializedName("_postman_id")
    val postmanID: String,
    val name: String,
    val schema: String
)

data class Item(
    val name: String,
    val id: String,
    val request: Request,
    val response: List<Response>
)

data class Request(
    val method: String,
    val header: List<Any?>,
    val url: String,
    val description: String? = null
)

data class Response(
    val id: String,
    val name: String,
    val originalRequest: Request,
    val status: String,
    val code: Long,
    @SerializedName("_postman_previewlanguage")
    val postmanPreviewlanguage: String,
    val header: List<Header>,
    val cookie: List<Cookie>,
    val body: String
)

data class Cookie(
    val expires: String
)

data class Header(
    val key: String,
    val value: String
)

data class Variable(
    val id: String,
    val key: String,
    val value: String
)
