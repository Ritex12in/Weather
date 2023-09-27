package com.example.weatherapp.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.weatherapp.R


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchCity=findViewById<SearchView>(R.id.svCity)
        val continueButton=findViewById<Button>(R.id.button)
        continueButton.setOnClickListener{
            val cityName=searchCity.query.toString()
            if (cityName.isNotEmpty()) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("CITY_NAME", cityName)
                startActivity(intent)
            } else {
                setSearchViewQueryHint(searchCity,getString(R.string.error))
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setSearchViewQueryHint(searchView: SearchView, hint: String) {
        searchView.queryHint = hint
    }
}

