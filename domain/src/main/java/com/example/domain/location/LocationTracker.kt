package com.example.domain.location

import com.example.domain.Resource
import kotlinx.coroutines.flow.Flow


interface LocationTracker {

    suspend fun getUserLocation(): Resource<CurrentLocation>
    suspend fun gpsStatus(): Flow<Boolean>

}