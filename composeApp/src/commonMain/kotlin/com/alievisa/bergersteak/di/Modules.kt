package com.alievisa.bergersteak.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.alievisa.bergersteak.data.local.BergerSteakDatabase
import com.alievisa.bergersteak.data.local.DatabaseFactory
import com.alievisa.bergersteak.data.network.BergerSteakRemoteDataSource
import com.alievisa.bergersteak.data.network.HttpClientFactory
import com.alievisa.bergersteak.domain.BergerSteakRepository
import com.alievisa.bergersteak.getLocalBaseUrl
import com.alievisa.bergersteak.ui.screens.main.MainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(HttpClientFactory::create)
    single { BergerSteakRemoteDataSource(baseUrl = getLocalBaseUrl(), httpClient = get()) }
    singleOf(::BergerSteakRepository)

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<BergerSteakDatabase>().mainDao }

    viewModelOf(::MainViewModel)
}