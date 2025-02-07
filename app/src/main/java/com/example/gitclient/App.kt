package com.example.gitclient

import android.app.Application
import com.example.gitclient.di.apiModule
import com.example.net.netModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(netModule, apiModule)}
    }
}