package com.example.mobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.model.AuthResponse
import com.example.mobile.data.model.LoginRequest
import com.example.mobile.data.model.RegisterRequest
import com.example.mobile.data.repository.AuthRepository
import com.example.mobile.ui.state.UiState
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableLiveData<UiState<AuthResponse>>(UiState.Idle)
    val loginState: LiveData<UiState<AuthResponse>> = _loginState

    private val _registerState = MutableLiveData<UiState<Unit>>(UiState.Idle)
    val registerState: LiveData<UiState<Unit>> = _registerState

    fun login(identifier: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.login(LoginRequest(identifier, password))
            _loginState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Login failed") }
            )
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        firstName: String?,
        lastName: String?
    ) {
        _registerState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.register(RegisterRequest(username, email, password, firstName, lastName))
            _registerState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Registration failed") }
            )
        }
    }

    fun resetRegisterState() {
        _registerState.value = UiState.Idle
    }
}
