package com.fireninja.lib_graphql.data.repository

import com.apollographql.apollo3.ApolloClient
import com.fireninja.AllTodosQuery
import com.fireninja.HelloQuery
import com.fireninja.lib_graphql.domain.repository.RemoteDataSource

class RemoteDataSourceImpl(private val apolloClient: ApolloClient) : RemoteDataSource {
  suspend fun hello(): String? {
    val response = apolloClient.query(HelloQuery()).execute()
    return response.data?.hello
  }

  suspend fun getAllTodos(): List<AllTodosQuery.Todo>? {
    return apolloClient.query(AllTodosQuery()).execute().data?.todos
  }
}