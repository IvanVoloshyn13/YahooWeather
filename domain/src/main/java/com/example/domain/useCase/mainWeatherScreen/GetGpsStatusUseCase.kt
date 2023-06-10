package com.example.domain.useCase.mainWeatherScreen

import com.example.domain.location.LocationTracker
import javax.inject.Inject

class GetGpsStatusUseCase @Inject constructor(private val locationTracker: LocationTracker) {

    suspend fun getGpsStatus() = locationTracker.gpsStatus()
}