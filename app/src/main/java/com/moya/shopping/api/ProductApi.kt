package com.moya.shopping.api

import com.moya.shopping.model.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("page") page: Int
    ): ProductSearchResponse
}
