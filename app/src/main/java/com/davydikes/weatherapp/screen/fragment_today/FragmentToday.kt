package com.davydikes.weatherapp.screen.fragment_today

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.davydikes.weatherapp.R
import com.davydikes.weatherapp.databinding.FragmentTodayBinding
import com.davydikes.weatherapp.support.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentToday : SupportFragmentInset<FragmentTodayBinding>(R.layout.fragment_today) {

    override val viewBinding: FragmentTodayBinding by viewBinding()
    private val viewModel: FragmentViewModelToday by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.coordinatesLatLiveData.observe(this.viewLifecycleOwner) { lat ->
            viewModel.coordinatesLonLiveData.observe(this.viewLifecycleOwner) { lon ->
                viewModel.networkConnectionLiveData.observe(this.viewLifecycleOwner) { isConnected ->
                    if (isConnected) {
                        viewModel.requestWeatherInfo(lat.toString(), lon.toString())
                        viewBinding.indicatorProgress.isVisible = true
                    }
                }
            }
        }


        viewModel.weatherInfoLiveData.observe(this.viewLifecycleOwner) { WeatherInfo ->

            if (WeatherInfo == null) {
                Toast.makeText(
                    requireContext(),
                    "It was happen something terrible",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val picasso = Picasso.Builder(requireContext())
                    .listener { _, _, exception -> exception.printStackTrace() }.build()

                picasso.load("$IMAGE_URI${WeatherInfo.weather[0].icon}@2x.png").fit()
                    .error(R.drawable.ic_cloud)
                    .into(viewBinding.ivWeather)


                val temp =
                    (WeatherInfo.main.temp - 273).toBigDecimal()
                        .setScale(1, RoundingMode.HALF_EVEN)
                val weather = WeatherInfo.weather[0].main

                val location = WeatherInfo.name

                viewBinding.tvLocation.text = "$location, ${WeatherInfo.sys.country}"

                viewBinding.tvWeatherTitle.text = "$temp | $weather"

                viewBinding.tvHumidity.text = "${WeatherInfo.main.humidity}%"

                viewBinding.tvPressure.text = "${WeatherInfo.main.pressure} hPa"

                viewBinding.tvClouds.text = "${WeatherInfo.clouds.all}%"

                viewBinding.tvGustWind.text = viewModel.checkDirectionOfTheWind(WeatherInfo.wind.deg)

                viewBinding.tvSpeedWind.text = "${WeatherInfo.wind.speed} km/h"

                viewBinding.indicatorProgress.isVisible = false



                viewBinding.btnShare.setOnClickListener {
                    val shareIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        val value =
                            "The temperature is $temp in $location and there is $weather"
                        this.putExtra(
                            Intent.EXTRA_TEXT,
                            value
                        )
                        this.type = "text/plain"
                    }
                    startActivity(shareIntent)
                }
            }
        }


    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarToday.setPadding(0, top, 0, 0)
    }


    companion object {
        const val IMAGE_URI = "http://openweathermap.org/img/wn/"
    }
}