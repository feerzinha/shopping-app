package com.moya.shopping.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val id: Int,
    @SerializedName("productName") val name: String,
    @SerializedName("salesPriceIncVat") val price: Float,
    @SerializedName("productImage") val image: String,
    @SerializedName("reviewInformation") val review: ReviewInformation,
    @SerializedName("USPs") val descriptions: List<String>,
    val availabilityState: Int,
    val nextDayDelivery: Boolean
)

data class ReviewInformation(
    @SerializedName("reviews") val reviews: List<String>,
    @SerializedName("reviewSummary") val summary: ReviewSummary
)

data class ReviewSummary(
    @SerializedName("reviewAverage") val average: Float,
    @SerializedName("reviewCount") val count: Int
)
