package com.fireninja.lib_graphql.domain.use_cases

import com.fireninja.lib_graphql.domain.use_cases.datastore.ReadAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.datastore.SaveAuthTokenUseCase

data class UseCases(
  val saveAuthTokenUseCase: SaveAuthTokenUseCase,
  val readAuthTokenUseCase: ReadAuthTokenUseCase,
)
