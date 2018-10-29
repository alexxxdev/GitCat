package com.github.alexxxdev.gitcat

import android.app.Application
import com.github.alexxxdev.gitcat.di.appModule
import com.github.alexxxdev.gitcat.di.authModule
import com.github.alexxxdev.gitcat.di.serviceModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, serviceModule, authModule))
    }
}