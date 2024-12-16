package com.security.securityapplication.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalData @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

     fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()
    }

     fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

     fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

     fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}