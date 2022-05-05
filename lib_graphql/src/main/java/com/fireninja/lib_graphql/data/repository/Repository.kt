package com.fireninja.lib_graphql.data.repository

import com.fireninja.AllTodosQuery
import com.fireninja.lib_graphql.domain.repository.DataStoreOperations
import com.fireninja.lib_graphql.domain.repository.RemoteDataSource
import com.fireninja.lib_graphql.domain.repository.SharedPreferencesSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
  private val remote: RemoteDataSource,
  private val dataStore: DataStoreOperations,
  private val sharedPreferences: SharedPreferencesSource,
) {
  suspend fun saveAuthToken(token: String) {
    dataStore.saveAuthToken(token)
  }

  fun readAuthToken(): Flow<String> {
    return dataStore.readAuthToken()
  }

  suspend fun getAuthStatus(): String? {
    return remote.getAuthStatus()
  }

  fun getBearerToken(): String {
    return sharedPreferences.getBearerToken()
  }

  fun setAuthToken(token: String) {
    return sharedPreferences.setBearerToken(token)
  }

  suspend fun getAllTasks(): List<AllTodosQuery.Todo> {
    return remote.getAllTodos() ?: emptyList()
  }

  suspend fun addNewTask() {
//    return remote.add() ?: emptyList()
  }
}