package com.fireninja.lib_graphql.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
  suspend fun saveAuthToken(token: String)
  fun readAuthToken(): Flow<String>
}