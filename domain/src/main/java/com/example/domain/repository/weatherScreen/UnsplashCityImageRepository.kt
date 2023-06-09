package com.example.domain.repository.weatherScreen

import com.example.domain.Resource

interface UnsplashCityImageRepository {

    suspend fun getCityImageByName(cityName: String): Resource<String>
}