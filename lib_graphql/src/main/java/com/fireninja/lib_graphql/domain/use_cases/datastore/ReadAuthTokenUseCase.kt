package com.fireninja.lib_graphql.domain.use_cases.datastore

import com.fireninja.lib_graphql.data.repository.Repository

class ReadAuthTokenUseCase(
  private val repository: Repository,
) {
  operator fun invoke() {
    repository.readAuthToken()
  }
}