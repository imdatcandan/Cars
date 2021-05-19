package com.imdatcandan.cars

import android.app.Application
import com.imdatcandan.cars.di.appModule
import com.imdatcandan.cars.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CarsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CarsApp)
            modules(arrayListOf(appModule, networkModule))
        }
    }
}