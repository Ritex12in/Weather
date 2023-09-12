package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    val base_URL="https://api.openweathermap.org/data/2.5/"
    val api_key="d294bd9ecf88bf952b8fc507eade15d8"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchCity=findViewById<SearchView>(R.id.svCity)
        val continueButton=findViewById<Button>(R.id.button)
        continueButton.setOnClickListener{
            val cityName=searchCity.toString().trim()
            if(cityName.isNotEmpty()){
                fetchWeatherData(cityName)
            }
            else{
                Toast.makeText(this,"Please enter a city name",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)

        val call = weatherApi.getWeatherData(cityName, api_key)
        call.enqueue(object : Callback<WeatherResponse> {
            fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val weatherData = response.body()
                    navigateToWeatherActivity(weatherData)
                } else {
                    Toast.makeText(this@SearchActivity, "City not found", Toast.LENGTH_SHORT).show()
                }
            }

            fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@SearchActivity, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun navigateToWeatherActivity(weatherData: WeatherResponse?) {
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra("weatherData", weatherData)
        startActivity(intent)
    }
}

