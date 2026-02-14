package com.example.mobile.data.repository

import com.example.mobile.data.api.AuthApi
import com.example.mobile.data.local.SessionManager
import com.example.mobile.data.model.UserProfile

class UserRepository(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) {
    suspend fun loadProfile(): Result<UserProfile> {
        val token = sessionManager.getAuthToken()?.trim()
        if (token.isNullOrEmpty()) {
            return Result.failure(IllegalStateException("Missing auth token"))
        }

        return try {
            val response = api.getCurrentUser("Bearer $token")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IllegalStateException("Empty profile response"))
                }
            } else {
                val message = response.errorBody()?.string()?.ifBlank { null } ?: "Profile request failed"
                Result.failure(IllegalArgumentException(message))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
