package com.fireninja.lib_graphql.domain.use_cases.tasks

import com.fireninja.lib_graphql.data.repository.Repository
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.type.EditTodoParams

class DeleteTaskUseCase(private val repository: Repository) {
  suspend operator fun invoke(taskId: Int) {
    return repository.deleteTask(taskId)
  }
}