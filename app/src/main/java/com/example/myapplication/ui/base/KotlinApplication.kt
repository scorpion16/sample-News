package com.example.myapplication.ui.base

import android.app.Application
import com.example.myapplication.di.generalModule
import com.example.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(generalModule, viewModelModule))
        }
    }
}