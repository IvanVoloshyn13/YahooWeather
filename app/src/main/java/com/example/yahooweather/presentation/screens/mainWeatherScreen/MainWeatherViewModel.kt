package com.example.yahooweather.presentation.screens.mainWeatherScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.GpsStatusReceiver
import com.example.domain.Resource
import com.example.domain.location.CurrentLocation
import com.example.domain.useCase.mainWeatherScreen.GetCityImageByNameUseCase
import com.example.domain.useCase.mainWeatherScreen.GetUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainWeatherViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getCityImageByNameUseCase: GetCityImageByNameUseCase,
    private val gpsStatusReceiver: GpsStatusReceiver
) : ViewModel() {




    private val _weatherState =
        MutableStateFlow(
            MainWeatherState(
                currentLocationState = Resource.Loading(),
                currentCityImage = Resource.Loading(),
                gpsState = null
            )
        )
    val weatherState = _weatherState.asStateFlow()




    init {
        getCurrentLocation()
    }


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
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getUserLocationUseCase.getCurrentUserLocation()) {
                is Resource.Success -> {
                    result.data.let { location ->
                        if (location != null) {
                            _weatherState.update { state ->
                                state.copy(
                                    currentLocationState = Resource.Success(
                                        data =
                                        CurrentLocation(
                                            latitude = location.latitude,
                                            longitude = location.longitude,
                                            cityName = location.cityName
                                        )
                                    )
                                )
                            }
                            getCityImage(location.cityName)
                        }

                    }
                }

                is Resource.Loading -> {
                    Log.e("Error", "Loading")
                }

                is Resource.Error -> {
                    _weatherState.update { state ->
                        state.copy(
                            currentLocationState = Resource.Error(message = result.message),
                            currentCityImage = Resource.Error(message = result.message)
                        )

                    }
                    if (result.message.equals("false")) {
//                        _weatherState.update { state ->
//                            state.copy(gpsState = true)
//                        }
                    }

                }
            }
        }
    }

    private fun getCityImage(cityName: String) {
        if (cityName.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
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
