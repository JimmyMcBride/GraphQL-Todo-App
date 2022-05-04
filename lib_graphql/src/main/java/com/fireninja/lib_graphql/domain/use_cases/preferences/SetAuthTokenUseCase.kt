package com.fireninja.lib_graphql.domain.use_cases.preferences

import com.fireninja.lib_graphql.data.repository.Repository

class SetAuthTokenUseCase(private val repository: Repository) {
  operator fun invoke(token: String) {
    return repository.setAuthToken(token)
  }
}