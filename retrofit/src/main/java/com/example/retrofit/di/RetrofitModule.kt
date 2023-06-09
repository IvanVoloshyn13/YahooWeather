package com.example.retrofit.di

import com.example.retrofit.api.UnsplashImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class RetrofitModule {
    @Provides
    @Singleton
    @RetrofitQualifier.UnsplashImageRetrofit
    fun provideUnsplashApiInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter("client_id", ApiConstants.UNSPLASH_API_KEY).build()
            val newRequest = originalRequest.newBuilder().url(newHttpUrl).build()
            chain.proceed(newRequest)

        }
    }

    @Provides
    @Singleton
    @RetrofitQualifier.UnsplashImageRetrofit
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @RetrofitQualifier.UnsplashImageRetrofit
    fun provideOkhttpClient(
        @RetrofitQualifier.UnsplashImageRetrofit loggingInterceptor: HttpLoggingInterceptor,
        @RetrofitQualifier.UnsplashImageRetrofit unsplashApiInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(unsplashApiInterceptor)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @RetrofitQualifier.UnsplashImageRetrofit
    fun provideCityImageRetrofit(@RetrofitQualifier.UnsplashImageRetrofit okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_UNSPLASH_URL).client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    @Provides
    @Singleton
    @RetrofitQualifier.UnsplashImageRetrofit
    fun provideUnsplashService(@RetrofitQualifier.UnsplashImageRetrofit retrofit: Retrofit): UnsplashImageService {
        return retrofit.create(UnsplashImageService::class.java)
    }

    companion object {
        object ApiConstants {
            const val BASE_UNSPLASH_URL = "https://api.unsplash.com/"
            const val UNSPLASH_API_KEY = "7SOcOVRhyNbg5rSUL6BlRsJ4UKR-9bQE0ew40bAooFA"

        }
    }
}