package com.example.data.repository

import com.example.domain.Resource
import com.example.domain.repository.weatherScreen.UnsplashCityImageRepository
import com.example.retrofit.api.UnsplashImageService
import com.example.retrofit.di.RetrofitQualifier
import javax.inject.Inject

class UnsplashImageRepositoryImpl @Inject constructor(
    @RetrofitQualifier.UnsplashImageRetrofit private val unsplashImageService: UnsplashImageService
) : UnsplashCityImageRepository {
    override suspend fun getCityImageByName(cityName: String): Resource<String> {
        val imageResponse = unsplashImageService.getImageByCity(cityName, 1)
        if (imageResponse.isSuccessful) {
            imageResponse.body()?.results?.get(0).let { imageItems ->
                if (imageItems != null) {
                    return Resource.Success(data = imageItems.urls.regular)
                } else return Resource.Loading()

            }
        } else {
            return Resource.Error(data = null, message = imageResponse.message())
        }
    }


}