package com.fireninja.lib_graphql.data.repository

import com.apollographql.apollo3.ApolloClient
import com.fireninja.HelloQuery
import com.fireninja.lib_graphql.domain.repository.DataStoreOperations
import com.fireninja.lib_graphql.domain.repository.RemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
  private val remote: RemoteDataSource,
  private val dataStore: DataStoreOperations
) {
  suspend fun saveAuthToken(token: String) {
    dataStore.saveAuthToken(token)
  }

  fun readAuthToken(): Flow<String> {
    return dataStore.readAuthToken()
  }
}