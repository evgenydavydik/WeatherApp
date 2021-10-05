package com.davydikes.weatherapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.davydikes.weatherapp.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore("${BuildConfig.APPLICATION_ID}_datastore")

class AppSettings(context: Context) {
    private val dataStore = context.dataStore

    fun coordinatesLonFlow(): Flow<Double> = dataStore.data.map { preferense ->
        preferense[doublePreferencesKey(LON_KEY)] ?: 0.0
    }

    fun coordinatesLatFlow(): Flow<Double> = dataStore.data.map { preferense ->
        preferense[doublePreferencesKey(LAT_KEY)] ?: 0.0
    }

    suspend fun getLat(): Double = coordinatesLatFlow().first()
    suspend fun getLon(): Double = coordinatesLonFlow().first()

    suspend fun setLon(lon: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(LON_KEY)] = lon
        }
    }

    suspend fun setLat(lat: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(LAT_KEY)] = lat
        }
    }

    companion object {
        private const val LON_KEY = "LON_KEY"
        private const val LAT_KEY = "LAT_KEY"
    }
}