package com.example.mobile.data.model

data class AuthResponse(
    val jwt: String,
    val username: String,
    val email: String
)
