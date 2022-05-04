package com.fireninja.lib_graphql.domain.repository

import android.content.Context

interface SharedPreferencesSource {
  fun getBearerToken(): String
  fun setBearerToken(token: String)
  fun removeBearerToken()
}