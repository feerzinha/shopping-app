package com.moya.shopping.di

import android.content.Context
import com.moya.shopping.api.ConnectivityInterceptor
import com.moya.shopping.api.ProductApi
import com.moya.shopping.repository.ProductRepository
import com.moya.shopping.search.ProductSearchViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_BASE_URL = "https://bdk0sta2n0.execute-api.eu-west-1.amazonaws.com/ios-assignment/"

private val repositoryModules = module {
    single { ProductRepository(get()) }
}

private val viewModelModules = module {
    viewModel { ProductSearchViewModel(get()) }
}

private val networkModules = module {
    single { providesOkHttpClient(get()) }
    single { providesRetrofit(get()) }
    single { provideProductApi(get()) }
}

fun providesOkHttpClient(context: Context): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(ConnectivityInterceptor(context))
    .build()

fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

val allModules = repositoryModules + viewModelModules + networkModules
