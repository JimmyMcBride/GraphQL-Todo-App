package com.fireninja.lib_graphql.domain.use_cases.auth

import com.fireninja.lib_graphql.data.repository.Repository

class GetAuthStatusUseCase(private val repository: Repository) {
  suspend operator fun invoke(): String? {
    return repository.getAuthStatus()
  }
}