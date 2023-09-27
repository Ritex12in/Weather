package com.example.weatherapp.Service
import android.view.View

class WA_loader(loaderLayout: View) {
    private val loaderLayout: View = loaderLayout
    fun showLoader() {
        loaderLayout.visibility = View.VISIBLE
    }
    fun hideLoader() {
        loaderLayout.visibility = View.GONE
    }
}