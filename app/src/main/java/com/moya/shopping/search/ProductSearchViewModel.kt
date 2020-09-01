package com.moya.shopping.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moya.shopping.model.Product
import com.moya.shopping.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductSearchViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Product>>? = null

    fun searchProductNew(queryString: String): Flow<PagingData<Product>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString

        val newResult: Flow<PagingData<Product>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)

        currentSearchResult = newResult
        return newResult
    }
}
