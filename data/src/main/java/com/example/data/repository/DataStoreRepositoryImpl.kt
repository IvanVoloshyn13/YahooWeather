package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.domain.repository.DataStoreRepository

import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreRepository {
    override suspend fun saveOnBoardSate(isComplete: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getOnBoardSate(): Boolean {
        TODO("Not yet implemented")
    }
}