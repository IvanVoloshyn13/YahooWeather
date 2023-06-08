package com.example.domain.repository

interface DataStoreRepository {
    suspend fun saveOnBoardSate(isComplete: Boolean)
    suspend fun getOnBoardSate(): Boolean
}