package com.fireninja.lib_graphql.domain.use_cases.datastore

import com.fireninja.lib_graphql.data.repository.Repository

class SaveAuthTokenUseCase(
  private val repository: Repository
) {
  suspend operator fun invoke(token: String) {
    repository.saveAuthToken(token)
  }
}