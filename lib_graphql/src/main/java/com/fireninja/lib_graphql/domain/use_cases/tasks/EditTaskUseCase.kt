package com.fireninja.lib_graphql.domain.use_cases.tasks

import com.fireninja.lib_graphql.data.repository.Repository
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.type.EditTodoParams

class EditTaskUseCase(private val repository: Repository) {
  suspend operator fun invoke(editTask: EditTodoParams): Task {
    return repository.editTask(editTask)
  }
}