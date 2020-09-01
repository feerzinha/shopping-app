package com.moya.shopping.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.moya.shopping.R
import com.moya.shopping.api.NoConnectivityException
import com.moya.shopping.utils.handleError
import com.moya.shopping.utils.setQueryListener
import kotlinx.android.synthetic.main.fragment_product_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ProductSearchFragment : Fragment() {

    private val viewModel: ProductSearchViewModel by inject()
    private val productAdapter by lazy { ProductSearchPagingAdapter() }
    private var currentQuery: String? = null
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_product_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        setupLoadStateListener()

        // Recover last search items in case of orientation changes.
        // (I was using LiveData on viewModel but with Paging 3 it wasn't working well)
        savedInstanceState?.getString(LAST_SEARCH_QUERY)?.let { searchProduct(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        configureSearchMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentQuery?.let { outState.putString(LAST_SEARCH_QUERY, currentQuery) }
    }

    private fun configureSearchMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setQueryListener { searchProduct(it) }
    }

    private fun setupList() = with(product_list) {
        layoutManager = LinearLayoutManager(context)
        adapter = productAdapter
    }

    private fun setupLoadStateListener() {
        lifecycleScope.launch {
            productAdapter.loadStateFlow.collectLatest { loadStates ->
                product_list_progress_bar.isVisible = loadStates.refresh is LoadState.Loading
                if (loadStates.refresh is LoadState.Error) {
                    handleError(
                        (loadStates.refresh as LoadState.Error).error is NoConnectivityException
                    )
                }
            }
        }
    }

    private fun searchProduct(query: String) {
        currentQuery = query
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchProductNew(query).collectLatest {
                productAdapter.submitData(it)
            }
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
    }
}
