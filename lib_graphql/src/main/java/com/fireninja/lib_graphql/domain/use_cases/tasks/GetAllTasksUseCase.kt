package com.fireninja.lib_graphql.domain.use_cases.tasks

import com.fireninja.AllTodosQuery
import com.fireninja.lib_graphql.data.repository.Repository
import com.fireninja.lib_graphql.domain.models.Task
import com.fireninja.lib_graphql.domain.models.toTask

class GetAllTasksUseCase(private val repository: Repository) {
    suspend operator fun invoke(): List<Task> {
        return repository.getAllTasks()
    }
}