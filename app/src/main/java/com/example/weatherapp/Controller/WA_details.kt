package com.example.weatherapp.Controller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.weatherapp.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
class WA_details : ComponentActivity(), OnMapReadyCallback{
    private var mGoogleMap:GoogleMap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wa_details)
        val cityName = intent.getStringExtra("CITY_NAME")
        Log.i("Mytag", cityName.toString())
        val mapFragment= supportFragmentManager.
        findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap=googleMap
    }
}
