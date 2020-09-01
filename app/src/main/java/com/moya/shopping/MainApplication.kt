package com.moya.shopping

import android.app.Application
import com.moya.shopping.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() = startKoin {
        androidContext(applicationContext)
        modules(allModules)
    }
}

