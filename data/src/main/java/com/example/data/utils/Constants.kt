package com.example.data.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

class Constants {
    object DataKey{
        const val DATA_PREF_KEY = "SharedKey"
    }
    object DataStoreValueKey{
        val IS_COMPLETE= booleanPreferencesKey("IS_COMPLETE")
    }
}