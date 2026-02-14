package com.example.mobile.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.model.UserProfile
import com.example.mobile.data.repository.UserRepository
import com.example.mobile.ui.state.UiState
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _profileState = MutableLiveData<UiState<UserProfile>>(UiState.Loading)
    val profileState: LiveData<UiState<UserProfile>> = _profileState

    fun loadProfile() {
        _profileState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.loadProfile()
            _profileState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Failed to load profile") }
            )
        }
    }
}
