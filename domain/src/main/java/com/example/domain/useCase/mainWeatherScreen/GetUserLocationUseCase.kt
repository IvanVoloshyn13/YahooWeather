package com.example.domain.useCase.mainWeatherScreen

import com.example.domain.Resource
import com.example.domain.location.CurrentLocation
import com.example.domain.location.LocationTracker
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val locationTracker: LocationTracker
) {

    suspend fun getCurrentUserLocation(): Resource<CurrentLocation> {
        return locationTracker.getUserLocation()
    }
}