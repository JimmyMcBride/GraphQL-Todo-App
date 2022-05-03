package com.fireninja.lib_graphql.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.fireninja.lib_graphql.data.repository.RemoteDataSourceImpl
import com.fireninja.lib_graphql.domain.repository.RemoteDataSource
import com.fireninja.lib_graphql.utils.AuthorizationInterceptor
import com.fireninja.lib_graphql.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
  @Singleton
  @Provides
  fun providesOkHttp(
    @ApplicationContext context: Context,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(AuthorizationInterceptor(context))
      .build()
  }

  @Singleton
  @Provides
  fun providesApolloClient(okHttpClient: OkHttpClient): ApolloClient =
    ApolloClient.Builder()
      .serverUrl(BASE_URL)
      .okHttpClient(okHttpClient)
      .build()

  @Singleton
  @Provides
  fun providesRemoteDataSource(apolloClient: ApolloClient): RemoteDataSource =
    RemoteDataSourceImpl(apolloClient)
}