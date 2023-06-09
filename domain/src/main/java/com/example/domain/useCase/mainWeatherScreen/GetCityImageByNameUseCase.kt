package com.example.domain.useCase.mainWeatherScreen

import com.example.domain.Resource
import com.example.domain.repository.weatherScreen.UnsplashCityImageRepository
import javax.inject.Inject

class GetCityImageByNameUseCase @Inject constructor(
    private val unsplashCityImageRepository: UnsplashCityImageRepository
) {
    suspend fun getImageByName(cityName: String): Resource<String> {
        return unsplashCityImageRepository.getCityImageByName(cityName = cityName)
    }
}