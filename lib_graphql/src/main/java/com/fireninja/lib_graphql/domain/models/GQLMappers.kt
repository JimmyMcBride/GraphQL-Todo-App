package com.fireninja.lib_graphql.domain.models

import com.fireninja.AddTodoMutation
import com.fireninja.AllTodosQuery
import com.fireninja.EditTodoMutation
import com.fireninja.TodoByIdQuery

fun AllTodosQuery.Todo.toTask(): Task {
  return Task(
    id = this.id,
    title = this.title,
    description = this.description,
    completed = this.completed
  )
}

fun AddTodoMutation.AddTodo.toTask(): Task {
  return Task(
    id = this.id,
    title = this.title,
    description = this.description,
    completed = this.completed
  )
}

fun TodoByIdQuery.Todo.toTask(): Task {
  return Task(
    id = this.id,
    title = this.title,
    description = this.description,
    completed = this.completed
  )
}

fun EditTodoMutation.EditTodo.toTask(): Task {
  return Task(
    id = this.id,
    title = this.title,
    description = this.description,
    completed = this.completed
  )
}