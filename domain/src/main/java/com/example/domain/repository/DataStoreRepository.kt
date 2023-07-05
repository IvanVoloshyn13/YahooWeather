package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnBoardSate(isComplete: Boolean)
    suspend fun getOnBoardSate(): Flow<Boolean>
}