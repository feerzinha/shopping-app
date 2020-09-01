package com.moya.shopping.repository

import androidx.paging.PagingSource
import com.moya.shopping.api.ProductApi
import com.moya.shopping.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ProductSearchPagingSource(
    private val productApi: ProductApi,
    private val query: String
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                withContext(Dispatchers.IO) {
                    productApi.searchProducts(query, nextPageNumber)
                }
            val products = response.items
            LoadResult.Page(
                data = products,
                prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber - 1,
                nextKey = if (products.isEmpty() || response.pageCount == nextPageNumber) null else nextPageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
