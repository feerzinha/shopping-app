package com.moya.shopping.model

import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
    val currentPage: Int,
    val pageCount: Int,
    @SerializedName("products") val items: List<Product> = emptyList(),
    val nextPage: Int? = null
)
