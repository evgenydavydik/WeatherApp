package com.davydikes.weatherapp.cloud

import com.davydikes.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CloudWeatherApi {
    @GET("weather")
    suspend fun importWeather(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("appid") apiId: String
    ): Response<WeatherCloudResult>

    @GET("forecast")
    suspend fun importForecastWeather(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("appid") apiId: String
    ): Response<WeatherForecastCloudResult>


    companion object {

        private const val API_URL = "https://api.openweathermap.org/data/2.5/"

        fun get(): CloudWeatherApi =
                Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create())
                        .client(
                                OkHttpClient.Builder().apply {
                                    if (BuildConfig.DEBUG) {
                                        addInterceptor(
                                                HttpLoggingInterceptor()
                                                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                                        )
                                    }
                                }.build()
                        ).build().create(CloudWeatherApi::class.java)
    }
}