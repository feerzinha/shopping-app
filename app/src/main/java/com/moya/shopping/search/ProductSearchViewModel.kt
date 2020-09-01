package com.moya.shopping.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moya.shopping.model.Product
import com.moya.shopping.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductSearchViewModel(
    private val repository: ProductRepository
): ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        searchProducts()
    }

    private fun searchProducts() {
        viewModelScope.launch {
            _products.value = repository.searchProduct()
        }
    }
}
