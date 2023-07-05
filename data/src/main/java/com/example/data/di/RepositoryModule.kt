package com.example.data.di

import com.example.data.repository.LocationTrackerImpl
import com.example.data.repository.UnsplashImageRepositoryImpl
import com.example.domain.location.LocationTracker
import com.example.domain.repository.weatherScreen.UnsplashCityImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        locationTrackerImpl: LocationTrackerImpl
    ): LocationTracker


    @Binds
    @Singleton
    abstract fun unsplashImageRepository(
        unsplashImageRepositoryImpl: UnsplashImageRepositoryImpl
    ): UnsplashCityImageRepository

}