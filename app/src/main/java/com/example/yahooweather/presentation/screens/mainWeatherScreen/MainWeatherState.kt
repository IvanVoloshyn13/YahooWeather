package com.example.yahooweather.presentation.screens.mainWeatherScreen

import com.example.domain.Resource
import com.example.domain.location.CurrentLocation

data class MainWeatherState(
    val currentLocationState: CurrentLocation,
    val currentCityImage: Resource<String>
)