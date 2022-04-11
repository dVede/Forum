package com.example.forum

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.Key

private val Context.dataStore by preferencesDataStore("data_store")

class UserPreferences(private val context: Context) {

    suspend fun saveAuthToken(authToken: String) {
        context.dataStore.edit { preference ->
            preference[KEY_AUTH] = authToken }
    }

    suspend fun clear() {
        context.dataStore.edit { preference ->
            preference.clear()
        }
    }

    val authToken: Flow<String?>
    get() = context.dataStore.data.map { preference ->
        preference[KEY_AUTH]
    }

    companion object {
        private  val KEY_AUTH = stringPreferencesKey("key_auth")
    }
}