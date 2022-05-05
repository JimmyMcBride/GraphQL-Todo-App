package com.fireninja.lib_graphql.domain.use_cases.tasks

import com.fireninja.AllTodosQuery
import com.fireninja.lib_graphql.data.repository.Repository

class AddNewTaskUseCase(private val repository: Repository) {
    suspend operator fun invoke() {
//        return repository.getAllTasks()
    }
}