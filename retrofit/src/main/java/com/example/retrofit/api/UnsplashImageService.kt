package com.example.retrofit.api

import com.example.retrofit.models.unsplash.UnsplashImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashImageService {
    @GET("search/photos/")
    suspend fun getImageByCity(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<UnsplashImageResponse>


}