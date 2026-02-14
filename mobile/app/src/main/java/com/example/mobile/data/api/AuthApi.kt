package com.example.mobile.data.api

import com.example.mobile.data.model.AuthResponse
import com.example.mobile.data.model.LoginRequest
import com.example.mobile.data.model.RegisterRequest
import com.example.mobile.data.model.UserProfile
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ResponseBody>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("user/me")
    suspend fun getCurrentUser(@Header("Authorization") authorization: String): Response<UserProfile>
}
