package com.arautos.confessioae.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Gerencia a persistência local de preferências do usuário utilizando DataStore.
 */
class UserPreferences(private val context: Context) {

    private object PreferencesKeys {
        val MY_CONDITION = stringPreferencesKey("my_condition")
        val MY_LAST_CONFESSION = longPreferencesKey("my_last_confession")
    }

    // Leitura dos dados
    val getCondition: Flow<String> = context.userPreferencesDataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.MY_CONDITION] ?: ""
        }

    val getLastConfession: Flow<Long?> = context.userPreferencesDataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.MY_LAST_CONFESSION]
        }

    // Escrita dos dados
    suspend fun saveCondition(condition: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.MY_CONDITION] = condition
        }
    }

    suspend fun saveLastConfession(dateMillis: Long) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.MY_LAST_CONFESSION] = dateMillis
        }
    }
}
