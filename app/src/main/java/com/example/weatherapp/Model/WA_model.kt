package com.example.weatherapp.Model

data class WeatherResponse(
    val name: String,
    val main: WeatherData,
    val sys: WeatherData,
    val coord: WeatherData,
    val wind: WeatherData
)

data class WeatherData(
    val temp: Double,
    val pressure: Int,
    val country: String,
    val lat: Double,
    val lon: Double,
    val speed: Double
)