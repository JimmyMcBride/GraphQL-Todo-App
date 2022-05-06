package com.fireninja.lib_graphql.domain.use_cases.tasks

import com.fireninja.AllTodosQuery
import com.fireninja.lib_graphql.data.repository.Repository
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.lib_graphql.domain.models.toTask
import com.fireninja.type.NewTodoParams

class AddNewTaskUseCase(private val repository: Repository) {
    suspend operator fun invoke(newTodo: NewTodoParams): Task {
        return repository.addNewTask(newTodo)
    }
}