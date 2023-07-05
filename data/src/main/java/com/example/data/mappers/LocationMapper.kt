package com.example.data.mappers

import android.location.Location
import com.example.domain.location.CurrentLocation

fun Location.toDomainLocation(cityName: String): CurrentLocation {
    return CurrentLocation(latitude = latitude, longitude = longitude, cityName = cityName)
}