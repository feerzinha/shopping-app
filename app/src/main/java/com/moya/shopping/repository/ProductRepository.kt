package com.moya.shopping.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moya.shopping.api.ProductApi
import com.moya.shopping.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productApi: ProductApi) {

    /**
     * Search products whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchResultStream(query: String): Flow<PagingData<Product>> {
        Log.d("ProductRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ProductSearchPagingSource(productApi, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}
