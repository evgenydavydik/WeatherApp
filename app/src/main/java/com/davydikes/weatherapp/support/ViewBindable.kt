package com.davydikes.weatherapp.support

import androidx.viewbinding.ViewBinding

interface ViewBindable<T : ViewBinding> {
    val viewBinding: T
}