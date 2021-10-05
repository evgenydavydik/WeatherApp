package com.davydikes.weatherapp

import android.app.Application
import com.davydikes.weatherapp.cloud.CloudWeatherApi
import com.davydikes.weatherapp.datastore.AppSettings
import com.davydikes.weatherapp.repository.CloudRepository
import com.davydikes.weatherapp.screen.fragment_forecast.FragmentViewModelForecast
import com.davydikes.weatherapp.screen.fragment_today.FragmentViewModelToday
import com.davydikes.weatherapp.support.NetworkConnection
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(listOf(cloudModule, viewModel, repository, barnModel, connection))
        }
    }

    private val viewModel = module {
        viewModel { FragmentViewModelToday(get(), get(), get()) }
        viewModel { MainActivityViewModel(get(), get()) }
        viewModel { FragmentViewModelForecast(get(), get()) }
    }

    private val cloudModule = module {
        factory { CloudWeatherApi.get() }

    }

    private val repository = module {
        factory { CloudRepository(get(), get()) }
    }

    private val barnModel = module {
        single { AppSettings(get()) }
    }

    private val connection = module {
        single { NetworkConnection(get()) }
    }
}