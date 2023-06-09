package com.example.yahooweather.presentation.screens.mainWeatherScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.location.CurrentLocation
import com.example.domain.useCase.mainWeatherScreen.GetCityImageByNameUseCase
import com.example.domain.useCase.mainWeatherScreen.GetUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainWeatherViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getCityImageByNameUseCase: GetCityImageByNameUseCase
) : ViewModel() {

    private val _weatherState =
        MutableStateFlow(
            MainWeatherState(
                currentLocationState = CurrentLocation(0.0, 0.0, ""),
                currentCityImage = Resource.Loading()
            )
        )
    val weatherState = _weatherState.asStateFlow()

    fun sendEvent(event: MainWeatherEvent) {
        when (event) {
         is MainWeatherEvent.CurrentLocationEvent -> {
                getCurrentLocation()
            }
           is MainWeatherEvent.CurrentCityImage -> {
                getCityImage(event.cityName)
            }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            when (val result = getUserLocationUseCase.getCurrentUserLocation()) {
                is Resource.Success -> {
                    result.data.let { location ->
                        if (location != null) {
                            _weatherState.update { state ->
                                state.copy(
                                    currentLocationState =
                                    CurrentLocation(
                                        latitude = location.latitude,
                                        longitude = location.longitude,
                                        cityName = location.cityName
                                    )
                                )
                            }
                        }
                    }
                }

                is Resource.Loading -> {
                    Log.e("Error", "Loading")
                }

                is Resource.Error -> {
                    Log.e("Error", "Error")
                }
            }
        }
    }

    private fun getCityImage(cityName: String) {
        if (cityName.isNotEmpty()) {
            viewModelScope.launch {
                val resource = getCityImageByNameUseCase.getImageByName(cityName = cityName)
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let { it ->
                            if (it != null)
                                _weatherState.update { state ->
                                    state.copy(currentCityImage = Resource.Success(data = it))
                                }
                        }
                    }

                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }


}
