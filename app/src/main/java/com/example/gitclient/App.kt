package com.example.gitclient

import android.app.Application
import com.example.gitclient.di.apiModule
import com.example.gitclient.di.netModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(netModule, apiModule) }
    }
}