package com.fireninja.lib_graphql.di

import android.content.Context
import com.fireninja.lib_graphql.data.repository.DataStoreOperationsImpl
import com.fireninja.lib_graphql.data.repository.Repository
import com.fireninja.lib_graphql.data.repository.SharedPreferencesSourceImpl
import com.fireninja.lib_graphql.domain.repository.DataStoreOperations
import com.fireninja.lib_graphql.domain.repository.SharedPreferencesSource
import com.fireninja.lib_graphql.domain.use_cases.preferences.GetAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.UseCases
import com.fireninja.lib_graphql.domain.use_cases.auth.GetAuthStatusUseCase
import com.fireninja.lib_graphql.domain.use_cases.datastore.ReadAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.datastore.SaveAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.preferences.SetAuthTokenUseCase
import com.fireninja.lib_graphql.domain.use_cases.tasks.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
  @Provides
  @Singleton
  fun provideDataStoreOperations(
    @ApplicationContext context: Context,
  ): DataStoreOperations {
    return DataStoreOperationsImpl(context)
  }

  @Provides
  @Singleton
  fun provideUseCases(repository: Repository): UseCases {
    return UseCases(
      saveAuthTokenUseCase = SaveAuthTokenUseCase(repository),
      readAuthTokenUseCase = ReadAuthTokenUseCase(repository),
      getAuthTokenUseCase = GetAuthTokenUseCase(repository),
      setAuthTokenUseCase = SetAuthTokenUseCase(repository),
      getAuthStatusUseCase = GetAuthStatusUseCase(repository),
      getAllTasksUseCase = GetAllTasksUseCase(repository),
      addNewTaskUseCase = AddNewTaskUseCase(repository),
      getTaskByIdUseCase = GetTaskByIdUseCase(repository),
      editTaskUseCase = EditTaskUseCase(repository),
      deleteTaskUseCase = DeleteTaskUseCase(repository),
    )
  }
}
