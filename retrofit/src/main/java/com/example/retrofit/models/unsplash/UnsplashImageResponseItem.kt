package com.example.retrofit.models.unsplash

data class UnsplashImageResponseItem(
    val alt_description: String,
    val color: String,
    val created_at: String,
    val description: String,
    val height: Int,
    val id: String,
    val urls: Urls,
    val width: Int
)