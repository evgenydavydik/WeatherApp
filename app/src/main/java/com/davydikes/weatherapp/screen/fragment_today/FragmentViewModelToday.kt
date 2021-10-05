package com.davydikes.weatherapp.screen.fragment_today

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.davydikes.weatherapp.datastore.AppSettings
import com.davydikes.weatherapp.models.WeatherInfo
import com.davydikes.weatherapp.repository.CloudRepository
import com.davydikes.weatherapp.support.CoroutineViewModel
import com.davydikes.weatherapp.support.NetworkConnection
import kotlinx.coroutines.launch

class FragmentViewModelToday(
    private val cloudRepository: CloudRepository,
    appSettings: AppSettings,
    networkConnection: NetworkConnection
) : CoroutineViewModel() {

    val weatherInfoLiveData = MutableLiveData<WeatherInfo?>()

    val coordinatesLatLiveData = appSettings.coordinatesLatFlow().asLiveData()

    val coordinatesLonLiveData = appSettings.coordinatesLonFlow().asLiveData()

    val networkConnectionLiveData = networkConnection

    fun requestWeatherInfo(lat: String, lon: String) {
        launch {
            weatherInfoLiveData.postValue(cloudRepository.importWeather(lat, lon))
        }
    }

    fun checkDirectionOfTheWind(deg: Long): String {
        val directionOfTheWind: String = when (deg.toFloat()) {
            in 348.75..11.25 -> "N"
            in 11.25..33.75 -> "NNE"
            in 33.75..56.25 -> "NE"
            in 56.25..78.75 -> "ENE"
            in 78.75..101.25 -> "E"
            in 101.25..123.75 -> "ESE"
            in 123.75..146.25 -> "SE"
            in 146.25..168.75 -> "SSE"
            in 168.75..191.25 -> "S"
            in 191.25..213.75 -> "SSW"
            in 213.75..236.25 -> "SW"
            in 236.25..258.75 -> "WSW"
            in 258.75..281.25 -> "W"
            in 281.25..303.75 -> "WNW"
            in 303.75..326.25 -> "NW"
            in 326.25..348.75 -> "NNW"
            else -> ""
        }

        return directionOfTheWind
    }


}