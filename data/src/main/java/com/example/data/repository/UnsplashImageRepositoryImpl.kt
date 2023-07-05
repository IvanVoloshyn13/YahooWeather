package com.example.data.repository

import com.example.domain.Resource
import com.example.domain.repository.weatherScreen.UnsplashCityImageRepository
import com.example.retrofit.api.UnsplashImageService
import com.example.retrofit.di.RetrofitQualifier
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class UnsplashImageRepositoryImpl @Inject constructor(
    @RetrofitQualifier.UnsplashImageRetrofit private val unsplashImageService: UnsplashImageService
) : UnsplashCityImageRepository {
    override suspend fun getCityImageByName(cityName: String): Resource<String> {
        val random = (0 until 4).random()
        val imageResponse = unsplashImageService.getImageByCity(cityName, random)
        return if (imageResponse.isSuccessful) {
            imageResponse.body()?.results.let { imageList ->
                if (imageList != null) {
                    val random = (0 until imageList.size).random()
                    return Resource.Success(data = imageList[random].urls.regular)

                } else return Resource.Error(message = imageResponse.message(), data = null)
            }
        } else Resource.Error(message = imageResponse.message(), data = null)


    }

    fun IntRange.random() = ThreadLocalRandom.current().nextInt((endInclusive + 1) - start) + start

}


