package com.davydikes.weatherapp.screen.fragment_today

import com.davydikes.weatherapp.R
import com.davydikes.weatherapp.databinding.FragmentTodayBinding
import com.davydikes.weatherapp.support.SupportFragmentInset
import by.kirich1409.viewbindingdelegate.viewBinding

class FragmentToday : SupportFragmentInset<FragmentTodayBinding>(R.layout.fragment_today) {

    override val viewBinding: FragmentTodayBinding by viewBinding()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarToday.setPadding(0, top, 0, 0)
    }


}