package com.davydikes.weatherapp.models

data class WeatherForecastInfo (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ViewHolderType>,
    val city: City
)