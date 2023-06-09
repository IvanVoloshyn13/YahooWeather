package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.utils.Constants
import com.example.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreRepository {
    override suspend fun saveOnBoardSate(isComplete: Boolean) {
        dataStore.edit { preferences ->
            preferences[Constants.DataStoreValueKey.IS_COMPLETE] = isComplete

        }
    }

    override suspend fun getOnBoardSate(): Flow<Boolean> {
        return dataStore.data.map {
            it.contains(Constants.DataStoreValueKey.IS_COMPLETE)
        }
    }
}