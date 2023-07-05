package com.example.yahooweather.presentation.screens.mainWeatherScreen

import android.text.BoringLayout
import com.example.domain.Resource
import com.example.domain.location.CurrentLocation

data class MainWeatherState(
    val currentLocationState: Resource<CurrentLocation>,
    val currentCityImage: Resource<String>,
    val gpsState: Boolean? = null
)