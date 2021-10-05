package com.davydikes.weatherapp

import android.location.Location
import com.davydikes.weatherapp.datastore.AppSettings
import com.davydikes.weatherapp.support.CoroutineViewModel
import com.davydikes.weatherapp.support.NetworkConnection
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val appSettings: AppSettings,
    networkConnection: NetworkConnection
) : CoroutineViewModel() {

    val networkConnectionLiveData = networkConnection

    fun saveLocation(locationResult: Location) {
        launch {
            appSettings.setLat(locationResult.latitude)
            appSettings.setLon(locationResult.longitude)
        }
    }


}