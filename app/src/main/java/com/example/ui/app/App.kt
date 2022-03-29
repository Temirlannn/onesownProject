package com.example.ui.app

import android.app.Application
import com.example.utils.networkConnectivityChecker.NetInternetOFF
import com.example.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetInternetOFF.init(this.applicationContext)
        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }
}