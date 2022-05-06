package com.fireninja.lib_graphql.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.fireninja.*
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.lib_graphql.domain.models.toTask
import com.fireninja.lib_graphql.domain.repository.RemoteDataSource
import com.fireninja.type.EditTodoParams
import com.fireninja.type.NewTodoParams

class RemoteDataSourceImpl(private val apolloClient: ApolloClient) :
  RemoteDataSource {
  override suspend fun getAllTodos(): List<Task>? {
    return apolloClient.query(AllTodosQuery())
      .execute().data?.todos?.map { it.toTask() }
  }

  override suspend fun addNewTask(newTodo: NewTodoParams):
      Task {
    try {
      val todo = apolloClient.mutation(AddTodoMutation(newTodo))
        .execute().data?.addTodo
      if (todo != null) {
        return todo.toTask()
      } else {
        throw Exception("Todo not added")
      }
    } catch (err: Exception) {
      throw Exception(err)
    }
  }

  override suspend fun getTaskById(taskId: Int): Task {
//    Log.d("RemoteDataSource", "getTaskById: $taskId")
    val response = apolloClient.query(TodoByIdQuery(taskId)).execute()
//    Log.d("RemoteDataSource", "getTaskById: ${response.data.toString()}")
//    Log.d("RemoteDataSource", "getTaskById: ${response.data?.todo.toString()}")
    return if (response.data?.todo != null) {
      response.data?.todo!!.toTask()
    } else {
      throw Exception("Could not get that todo")
    }
  }

  override suspend fun updateTask(editTaskParams: EditTodoParams): Task {
    Log.d("RemoteDataSource", "getTaskById: $editTaskParams")
    val response =
      apolloClient.mutation(EditTodoMutation(editTaskParams)).execute()
    Log.d("RemoteDataSource", "getTaskById: ${response.data.toString()}")
    Log.d("RemoteDataSource", "getTaskById: ${response.data?.editTodo.toString()}")
    return if (response.data?.editTodo != null) {
      response.data?.editTodo!!.toTask()
    } else {
      throw Exception("Could not get that todo")
    }
  }

  override suspend fun deleteTask(taskId: Int) {
    apolloClient.mutation(DeleteTodoMutation(taskId)).execute()
  }

  override suspend fun getAuthStatus(): String? {
    val response = apolloClient.query(HelloQuery()).execute()
    return response.data?.hello
  }
}