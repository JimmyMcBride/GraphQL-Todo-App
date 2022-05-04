package com.fireninja.lib_graphql.domain.repository

import com.fireninja.AllTodosQuery

interface RemoteDataSource {
  suspend fun getAuthStatus(): String?
  suspend fun getAllTodos(): List<AllTodosQuery.Todo>?
}