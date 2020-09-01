package com.moya.shopping.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.moya.shopping.R
import kotlinx.android.synthetic.main.fragment_product_search.*
import org.koin.android.ext.android.inject

class ProductSearchFragment: Fragment() {

    private val viewModel: ProductSearchViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupList()
    }

    private fun setupList() {
        with(product_list) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ProductSearchAdapter()
        }
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            (product_list.adapter as? ProductSearchAdapter)?.updateData(products)
        })
    }
}
