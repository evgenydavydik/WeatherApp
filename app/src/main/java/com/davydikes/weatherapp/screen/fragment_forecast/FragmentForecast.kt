package com.davydikes.weatherapp.screen.fragment_forecast

import com.davydikes.weatherapp.R
import com.davydikes.weatherapp.databinding.FragmentForecastBinding
import com.davydikes.weatherapp.support.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding

class FragmentForecast : SupportFragmentInset<FragmentForecastBinding>(R.layout.fragment_forecast) {

    override val viewBinding: FragmentForecastBinding by viewBinding()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarForecast.setPadding(0, top, 0, 0)
    }


}