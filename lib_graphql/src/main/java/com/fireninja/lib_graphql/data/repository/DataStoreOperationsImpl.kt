package com.fireninja.lib_graphql.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.fireninja.lib_graphql.domain.repository.DataStoreOperations
import com.fireninja.lib_graphql.utils.Constants.PREFERENCES_KEY
import com.fireninja.lib_graphql.utils.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context): DataStoreOperations {
  private object PreferencesKey {
    val userTokenKey = stringPreferencesKey(name = PREFERENCES_KEY)
  }

  private val dataStore = context.dataStore

  override suspend fun saveAuthToken(token: String) {
    dataStore.edit { preferences ->
      preferences[PreferencesKey.userTokenKey] = token
    }
  }

  override fun readAuthToken(): Flow<String> {
    return dataStore.data
      .catch { exception ->
        if (exception is IOException) {
          emit(emptyPreferences())
        } else {
          throw exception
        }
      }
      .map { preferences ->
        val onBoardingState = preferences[PreferencesKey.userTokenKey] ?: ""
        onBoardingState
      }
  }
}