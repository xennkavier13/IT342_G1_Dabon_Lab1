package com.example.mobile.data.local

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getAuthToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUserSummary(
        username: String,
        email: String,
        firstName: String? = null,
        lastName: String? = null,
        createdAt: String? = null
    ) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_EMAIL, email)
            .putString(KEY_FIRST_NAME, firstName)
            .putString(KEY_LAST_NAME, lastName)
            .putString(KEY_CREATED_AT, createdAt)
            .apply()
    }

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)

    fun getFirstName(): String? = prefs.getString(KEY_FIRST_NAME, null)

    fun getLastName(): String? = prefs.getString(KEY_LAST_NAME, null)

    fun getCreatedAt(): String? = prefs.getString(KEY_CREATED_AT, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "auth_session"
        private const val KEY_TOKEN = "token"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_CREATED_AT = "created_at"
    }
}
