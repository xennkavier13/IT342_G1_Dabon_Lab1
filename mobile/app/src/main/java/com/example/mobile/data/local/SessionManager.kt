package com.example.mobile.data.local

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getAuthToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUserSummary(username: String, email: String) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "auth_session"
        private const val KEY_TOKEN = "token"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
    }
}
