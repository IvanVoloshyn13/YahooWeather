package com.example.yahooweather.presentation.screens.mainWeatherScreen

sealed class MainWeatherEvent {
    object CurrentLocationEvent : MainWeatherEvent()
    class CurrentCityImage(val cityName: String) : MainWeatherEvent()

}