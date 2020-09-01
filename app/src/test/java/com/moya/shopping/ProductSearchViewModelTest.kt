package com.moya.shopping

import androidx.paging.PagingData
import com.moya.shopping.model.Product
import com.moya.shopping.repository.ProductRepository
import com.moya.shopping.search.ProductSearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductSearchViewModelTest {

    @RelaxedMockK
    private lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductSearchViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductSearchViewModel(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should get search result stream`() = runBlockingTest {
        val fakeQuery = "queryValue"
        val streamResponse: Flow<PagingData<Product>> = mockk(relaxed = true)
        every { repository.getSearchResultStream(fakeQuery) } returns streamResponse

        viewModel.searchProductNew(fakeQuery).collect {
            assertEquals(streamResponse, it)
        }
    }
}
