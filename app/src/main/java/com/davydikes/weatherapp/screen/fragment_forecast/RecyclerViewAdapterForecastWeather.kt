package com.davydikes.weatherapp.screen.fragment_forecast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davydikes.weatherapp.R
import com.davydikes.weatherapp.models.ViewHolderType
import com.davydikes.weatherapp.models.WeatherForecastInfo
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapterForecastWeather(
    private val weatherForecastInfo: WeatherForecastInfo
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FORECAST_HOLDER) {
            ForecastWeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_forecast, parent, false)
            )
        } else {
            DayWeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_day, parent, false)
            )
        }

    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (getItemViewType(position) == TYPE_FORECAST_HOLDER) {
            (holder as ForecastWeatherViewHolder)
                .bind(weatherForecastInfo.list[position] as ViewHolderType.ListElement)
        } else {
            (holder as DayWeatherViewHolder)
                .bind(weatherForecastInfo.list[position] as ViewHolderType.Day)
        }
    }

    override fun getItemCount(): Int {
        return weatherForecastInfo.list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (weatherForecastInfo.list[position]) {
            is ViewHolderType.ListElement -> weatherForecastInfo.list[position].viewType
            is ViewHolderType.Day -> weatherForecastInfo.list[position].viewType
        }
    }

    inner class ForecastWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTime = itemView.findViewById<TextView>(R.id.tvTime)

        private val imWeather = itemView.findViewById<ImageView>(R.id.imageWeather)

        private val tvWeather = itemView.findViewById<TextView>(R.id.tvWeather)

        private val tvTemperature = itemView.findViewById<TextView>(R.id.tvTemperature)


        @SuppressLint("SetTextI18n")
        fun bind(item: ViewHolderType.ListElement) {

            Picasso.get().load("${IMAGE_URI}${item.weather[0].icon}@2x.png")
                .error(R.drawable.ic_cloud).into(imWeather)
            tvTime.text = horsFormatter.format(Date(item.dt * 1000))
            tvWeather.text = item.weather[0].description
            tvTemperature.text = "${
                (item.main.temp - 273).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            } °C"
        }
    }

    inner class DayWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDay = itemView.findViewById<TextView>(R.id.tvDay)


        fun bind(item: ViewHolderType.Day) {
            tvDay.text = item.day

        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        val horsFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        const val IMAGE_URI = "http://openweathermap.org/img/wn/"
        const val TYPE_FORECAST_HOLDER = 0
    }

}