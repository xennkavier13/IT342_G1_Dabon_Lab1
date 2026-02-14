package com.example.mobile.data.model

data class UserProfile(
    val userId: Int,
    val username: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val createdAt: String?
)
