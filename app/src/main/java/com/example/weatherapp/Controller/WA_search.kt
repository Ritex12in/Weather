package com.example.weatherapp.Controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.ComponentActivity
import com.example.weatherapp.R
import com.example.weatherapp.Service.WA_service
import android.util.Log
import android.view.View
import android.content.Intent
import android.widget.Button
import com.example.weatherapp.Service.WA_loader
import com.example.weatherapp.Service.keyboard

class WA_search : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wa_search)
        val loaderLayout = findViewById<View>(R.id.loaderLayout)
        val loader = WA_loader(loaderLayout)
        val rootView = findViewById<View>(android.R.id.content)
        val searchCity = findViewById<SearchView>(R.id.svCity)
        val knowMore = findViewById<Button>(R.id.button)
        rootView.setOnClickListener {
            keyboard.hideKeyboard(searchCity,this)
        }
        knowMore.setOnClickListener{
            val intent = Intent(this, WA_details::class.java)
            val cityName = searchCity.query.toString()
            intent.putExtra("CITY_NAME", cityName)
            startActivity(intent)
        }
        searchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    Log.i("MYTAG", query)
                    loader.showLoader()
                    WA_service.fetchWeatherData(
                        query,
                        findViewById(R.id.cityName),
                        findViewById(R.id.temp),
                        findViewById(R.id.description),
                        findViewById(R.id.weatherImage),
                        this@WA_search)
                    loader.hideLoader()
                    keyboard.hideKeyboard(searchCity,this@WA_search)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}



