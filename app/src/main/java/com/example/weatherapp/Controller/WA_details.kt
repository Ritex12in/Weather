package com.example.weatherapp.Controller

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.weatherapp.R
import com.example.weatherapp.Model.WeatherResponse
import com.example.weatherapp.Service.WeatherService
import com.example.weatherapp.Config.WeatherConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class SecondActivity : ComponentActivity() {

    lateinit var cityTextView: TextView
    lateinit var tempTextView: TextView
    lateinit var countryTextView: TextView
    lateinit var pressureTextView: TextView
    lateinit var latTextView: TextView
    lateinit var lonTextView: TextView
    lateinit var windTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        cityTextView = findViewById<TextView>(R.id.cityName)
        countryTextView = findViewById<TextView>(R.id.countryName)
        tempTextView = findViewById<TextView>(R.id.temp)
        pressureTextView = findViewById<TextView>(R.id.pressureValue)
        latTextView = findViewById<TextView>(R.id.latValue)
        lonTextView = findViewById<TextView>(R.id.lonValue)
        windTextView = findViewById<TextView>(R.id.windValue)
        val cityName = intent.getStringExtra("CITY_NAME")
        cityTextView.text = cityName
        fetchWeatherData(cityName)
    }

    private fun fetchWeatherData(cityName: String?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(WeatherConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call: Call<WeatherResponse> = service.getWeatherData(cityName, WeatherConfig.API_KEY)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    if (weatherData != null) {
                        cityTextView.text = weatherData?.name
                        val temperatureInCelsius =
                            weatherData?.main?.temp?.toFloat()?.minus(273.15f)
                        val formattedTemperature = "%.2f".format(temperatureInCelsius)
                        tempTextView.text = "${formattedTemperature?.toString()}"
                        countryTextView.text = weatherData?.sys?.country
                        latTextView.text = weatherData?.coord?.lat?.toString()
                        lonTextView.text = weatherData?.coord?.lon?.toString()
                        pressureTextView.text = weatherData?.main?.pressure?.toString()
                        windTextView.text = weatherData?.wind?.speed?.toString()
                    } else {
                        Toast.makeText(
                            this@SecondActivity,
                            getString(R.string.data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(
                            this@SecondActivity, getString(R.string.cityName),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@SecondActivity,
                            getString(R.string.response),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(
                    this@SecondActivity,
                    getString(R.string.apifailed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}