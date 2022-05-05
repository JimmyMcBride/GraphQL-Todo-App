package com.fireninja.lib_graphql.domain.use_cases

import com.fireninja.lib_graphql.domain.use_cases.auth.GetAuthStatusUseCase
import com.fireninja.lib_graphql.domain.use_cases.datastore.ReadAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.datastore.SaveAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.preferences.GetAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.preferences.SetAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.tasks.GetAllTasksUseCase

data class UseCases(
  val saveAuthTokenUseCase: SaveAuthTokenUseCase,
  val readAuthTokenUseCase: ReadAuthTokenUseCase,
  val getAuthTokenUseCase: GetAuthTokenUseCase,
  val setAuthTokenUseCase: SetAuthTokenUseCase,
  val getAuthStatusUseCase: GetAuthStatusUseCase,
  val getAllTasksUseCase: GetAllTasksUseCase,
)
