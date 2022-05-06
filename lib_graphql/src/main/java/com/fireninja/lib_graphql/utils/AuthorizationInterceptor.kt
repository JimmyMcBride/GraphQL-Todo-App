package com.fireninja.lib_graphql.utils

import com.fireninja.lib_graphql.domain.repository.SharedPreferencesSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val sharedPreferences: SharedPreferencesSource) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .addHeader("Authorization", sharedPreferences.getBearerToken())
      .build()

    return chain.proceed(request)
  }
}