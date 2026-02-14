package com.example.mobile.data.repository

import com.example.mobile.data.api.AuthApi
import com.example.mobile.data.local.SessionManager
import com.example.mobile.data.model.AuthResponse
import com.example.mobile.data.model.LoginRequest
import com.example.mobile.data.model.RegisterRequest

class AuthRepository(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) {
    suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    sessionManager.saveAuthToken(body.jwt)
                    sessionManager.saveUserSummary(body.username, body.email)
                    Result.success(body)
                } else {
                    Result.failure(IllegalStateException("Empty response"))
                }
            } else {
                val message = response.errorBody()?.string()?.ifBlank { null } ?: "Login failed"
                Result.failure(IllegalArgumentException(message))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun register(request: RegisterRequest): Result<Unit> {
        return try {
            val response = api.register(request)
            if (response.isSuccessful) {
                response.body()?.close()
                Result.success(Unit)
            } else {
                val message = response.errorBody()?.string()?.ifBlank { null } ?: "Registration failed"
                Result.failure(IllegalArgumentException(message))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
