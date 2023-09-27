package com.example.weatherapp.Service

import com.example.weatherapp.R
class WA_images {
    fun getWeatherConditionImageId(id: Int): Int {
        return when (id) {
            in 800..803 -> R.drawable.cloudy
            in 500..599 -> R.drawable.rainy
            in 600..699 -> R.drawable.snowy
            in 804..999 -> R.drawable.sunny
            else -> R.drawable.sunny
        }
    }
}
