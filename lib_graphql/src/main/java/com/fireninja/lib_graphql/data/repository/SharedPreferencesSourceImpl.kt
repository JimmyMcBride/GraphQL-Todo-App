package com.fireninja.lib_graphql.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.fireninja.lib_graphql.domain.repository.SharedPreferencesSource

class SharedPreferencesSourceImpl(private val context: Context): SharedPreferencesSource {
  private val keyToken = "TOKEN"
  private fun preferences(context: Context): SharedPreferences {
    val masterKeyAlias: String =
      MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    return EncryptedSharedPreferences.create(
      "secret_shared_prefs",
      masterKeyAlias,
      context,
      EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
      EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
  }
  override fun getBearerToken(): String {
    val token = preferences(context).getString(keyToken, null)
    return if (token.isNullOrBlank()) {
      ""
    } else {
      "Bearer " + preferences(context).getString(keyToken, null)
    }
  }

  override fun setBearerToken(token: String) {
    preferences(context).edit().apply {
      putString(keyToken, token)
      apply()
    }
  }

  override fun removeBearerToken() {
    preferences(context).edit().apply {
      remove(keyToken)
      apply()
    }
  }
}