package com.example.mobile.core.di

import android.content.Context
import com.example.mobile.core.network.ApiClient
import com.example.mobile.data.api.AuthApi
import com.example.mobile.data.local.SessionManager
import com.example.mobile.data.repository.AuthRepository
import com.example.mobile.data.repository.UserRepository
import com.example.mobile.ui.auth.AuthViewModelFactory
import com.example.mobile.ui.dashboard.DashboardViewModelFactory

object ServiceLocator {
    private fun authApi(): AuthApi = ApiClient.retrofit.create(AuthApi::class.java)

    fun sessionManager(context: Context): SessionManager {
        return SessionManager(context.applicationContext)
    }

    fun authRepository(context: Context): AuthRepository {
        return AuthRepository(authApi(), sessionManager(context))
    }

    fun userRepository(context: Context): UserRepository {
        return UserRepository(authApi(), sessionManager(context))
    }

    fun authViewModelFactory(context: Context): AuthViewModelFactory {
        return AuthViewModelFactory(authRepository(context))
    }

    fun dashboardViewModelFactory(context: Context): DashboardViewModelFactory {
        return DashboardViewModelFactory(userRepository(context))
    }
}
