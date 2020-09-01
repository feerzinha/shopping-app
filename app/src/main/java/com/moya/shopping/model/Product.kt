package com.moya.shopping.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val id: Int,
    @SerializedName("productName") val name: String,
    val reviewInformation: ReviewInformation,
    @SerializedName("USPs") val usps : List<String>,
    val availabilityState: Int,
    val salesPriceIncVat: String,
    @SerializedName("productImage") val image: String,
    val nextDayDelivery: Boolean
)

data class ReviewInformation(
    val reviews: List<String>,
    val reviewSummary: ReviewSummary
)

data class ReviewSummary(
    val reviewAverage: Float,
    val reviewCount: Int
)
