package com.davydikes.weatherapp.screen.fragment_forecast

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.davydikes.weatherapp.R
import com.davydikes.weatherapp.databinding.FragmentForecastBinding
import com.davydikes.weatherapp.support.SupportFragmentInset
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentForecast : SupportFragmentInset<FragmentForecastBinding>(R.layout.fragment_forecast) {
    override val viewBinding: FragmentForecastBinding by viewBinding()
    private val viewModel: FragmentViewModelForecast by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.networkConnectionLiveData.observe(this.viewLifecycleOwner) {
            if (it) {
                viewModel.requestForecastWeatherInfo()
                viewBinding.indicatorProgress.isVisible = true
            }

        }

        viewModel.weatherForecastInfoLiveData.observe(this.viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    "It was happen something terrible",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val adapter = RecyclerViewAdapterForecastWeather(it)
                viewBinding.recyclerViewWeather.adapter = adapter
                viewBinding.toolbarTitle.text = it.city.name
                viewBinding.indicatorProgress.isVisible = false
            }

        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarForecast.setPadding(0, top, 0, 0)

    }
}