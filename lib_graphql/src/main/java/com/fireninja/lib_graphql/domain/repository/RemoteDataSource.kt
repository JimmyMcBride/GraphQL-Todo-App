package com.fireninja.lib_graphql.domain.repository

import com.fireninja.AddTodoMutation
import com.fireninja.AllTodosQuery
import com.fireninja.TodoByIdQuery
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.type.EditTodoParams
import com.fireninja.type.NewTodoParams

interface RemoteDataSource {
  suspend fun getAuthStatus(): String?
  suspend fun getAllTodos(): List<Task>?
  suspend fun addNewTask(newTodo: NewTodoParams): Task
  suspend fun getTaskById(taskId: Int): Task
  suspend fun updateTask(editTaskParams: EditTodoParams): Task
  suspend fun deleteTask(taskId: Int)
}