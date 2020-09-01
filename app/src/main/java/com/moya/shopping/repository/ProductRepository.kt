package com.moya.shopping.repository

import com.moya.shopping.api.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productApi: ProductApi) {

    suspend fun searchProduct() =
        withContext(Dispatchers.IO) {
            productApi.searchProducts("apple", 1).items
        }
}
