package com.davydikes.weatherapp.cloud

import com.davydikes.weatherapp.models.*

data class WeatherForecastCloudResult(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ViewHolderType.ListElement>,
    val city: City
)

data class WeatherCloudResult(

    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)