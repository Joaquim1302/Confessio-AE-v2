package com.arautos.confessioae.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "confessio_prefs")

class ConfessioRepository(private val context: Context) {

    private object PreferencesKeys {
        val SELECTED_IDS = stringSetPreferencesKey("selected_ids")
        val CUSTOM_ITEMS = stringPreferencesKey("custom_items")
    }

    val selectedIds: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SELECTED_IDS] ?: emptySet()
        }

    val customItemsJson: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.CUSTOM_ITEMS]
        }

    suspend fun updateSelectedIds(ids: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_IDS] = ids
        }
    }

    suspend fun updateCustomItemsJson(json: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CUSTOM_ITEMS] = json
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
