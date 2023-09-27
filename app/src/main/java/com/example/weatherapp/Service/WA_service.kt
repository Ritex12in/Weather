package com.example.weatherapp.Service

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.weatherapp.Config.WA_config
import com.example.weatherapp.Model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.weatherapp.R


interface WA_service {
    @GET("weather")
    fun getWeatherData(
        @Query("q") cityName: String?,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

    companion object {
        var alertShown = false
        fun fetchWeatherData(
            cityName: String?,
            cityTextView: TextView?,
            tempTextView: TextView?,
            countryTextView: TextView?,
            descriptionTextView: TextView?,
            latTextView: TextView?,
            lonTextView: TextView?,
            pressureTextView: TextView?,
            windTextView: TextView?,
            context: Context
        ) {
            if (alertShown) {
                return
            }
            val retrofit = Retrofit.Builder()
                .baseUrl(WA_config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WA_service::class.java)
            val call: Call<WeatherResponse> = service.getWeatherData(cityName, WA_config.API_KEY)

            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        if (weatherData != null) {
                            cityTextView?.text = weatherData?.name
                            val temperatureInCelsius =
                                weatherData?.main?.temp?.toFloat()?.minus(273.15f)
                            val formattedTemperature = "%.2f".format(temperatureInCelsius)
                            tempTextView?.text = "${formattedTemperature?.toString()}"
                            countryTextView?.text = weatherData?.sys?.country
                            latTextView?.text = weatherData?.coord?.lat?.toString()
                            lonTextView?.text = weatherData?.coord?.lon?.toString()
                            pressureTextView?.text = weatherData?.main?.pressure?.toString()
                            windTextView?.text = weatherData?.wind?.speed?.toString()
                            descriptionTextView?.text =
                                weatherData?.weather?.description?.toString()
                            val imm =
                                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(cityTextView?.windowToken, 0)
                        } else {
                            WA_warning.showAlert(context, context.getString(R.string.title), context.getString(R.string.data)
                            )
                        }
                    } else {
                        if (!alertShown) {
                            WA_warning.showAlert(context, context.getString(R.string.title), context.getString(R.string.cityName)
                            )
                            alertShown = true
                        }
                    }
                }
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    WA_warning.showAlert(context, context.getString(R.string.title), context.getString(R.string.apifailed)
                    )
                }
            })
        }
    }
}
