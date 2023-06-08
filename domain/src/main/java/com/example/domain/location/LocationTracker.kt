package com.example.domain.location

import com.example.domain.Resource

interface LocationTracker {

    suspend fun getUserLocation(): Resource<CurrentLocation>

}