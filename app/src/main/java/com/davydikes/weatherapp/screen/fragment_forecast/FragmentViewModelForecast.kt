package com.davydikes.weatherapp.screen.fragment_forecast

import androidx.lifecycle.MutableLiveData
import com.davydikes.weatherapp.models.WeatherForecastInfo
import com.davydikes.weatherapp.repository.CloudRepository
import com.davydikes.weatherapp.support.CoroutineViewModel
import com.davydikes.weatherapp.support.NetworkConnection
import kotlinx.coroutines.launch

class FragmentViewModelForecast(
    private val cloudRepository: CloudRepository,
    networkConnection: NetworkConnection
) : CoroutineViewModel() {

    val weatherForecastInfoLiveData = MutableLiveData<WeatherForecastInfo?>()
    val networkConnectionLiveData = networkConnection

    fun requestForecastWeatherInfo() {
        launch {
            weatherForecastInfoLiveData.postValue(cloudRepository.importForecastWeather())
        }
    }
}