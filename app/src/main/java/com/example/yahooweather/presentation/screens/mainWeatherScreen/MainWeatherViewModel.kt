package com.example.yahooweather.presentation.screens.mainWeatherScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.mainWeatherScreen.GetUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainWeatherViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch { getUserLocationUseCase.getCurrentUserLocation() }

    }


    private val _weatherState = MutableStateFlow(MainWeatherState(permissionIsGranted = 0))
    val weatherState = _weatherState.asStateFlow()

    fun sendEvent(event: MainWeatherEvent) {
    }


}
