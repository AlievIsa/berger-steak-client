package com.alievisa.bergersteak

import android.app.Application
import com.alievisa.bergersteak.di.initKoin
import org.koin.android.ext.koin.androidContext

class BergerSteakApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BergerSteakApp)
        }
    }
}