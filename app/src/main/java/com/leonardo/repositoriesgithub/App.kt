package com.leonardo.repositoriesgithub

import android.app.Application
import com.leonardo.repositoriesgithub.data.di.DataModule
import com.leonardo.repositoriesgithub.domain.di.DomainModule
import com.leonardo.repositoriesgithub.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()

    }


}