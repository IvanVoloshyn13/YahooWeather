package com.example.retrofit.di

import javax.inject.Qualifier

sealed interface RetrofitQualifier {

    @Qualifier
    @Retention(value = AnnotationRetention.RUNTIME)
    annotation class UnsplashImageRetrofit()
}