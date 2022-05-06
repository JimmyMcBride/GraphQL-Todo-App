package com.fireninja.lib_graphql.domain.use_cases.tasks

import com.fireninja.AllTodosQuery
import com.fireninja.lib_graphql.data.repository.Repository
import com.fireninja.lib_graphql.domain.models.Task

class GetTaskByIdUseCase(private val repository: Repository) {
  suspend operator fun invoke(taskId: Int): Task {
    return repository.getTaskById(taskId)
  }
}