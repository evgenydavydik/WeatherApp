package com.davydikes.weatherapp.models

import com.google.gson.annotations.SerializedName

sealed class ViewHolderType(val viewType: Int) {

    data class Day(val day: String) : ViewHolderType(1)

    data class ListElement(
        val dt: Long,
        val main: MainClass,
        val weather: List<Weather>,
        val clouds: Clouds,
        val wind: Wind,
        val visibility: Long,
        val pop: Double,
        val sys: Sys,

        @SerializedName("dt_txt")
        val dtTxt: String,

        val rain: Rain? = null
    ) : ViewHolderType(0)


}