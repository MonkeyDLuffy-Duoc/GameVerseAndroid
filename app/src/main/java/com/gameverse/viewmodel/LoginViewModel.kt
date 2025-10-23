package com.gameverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameverse.data.repository.AppRepository
import com.gameverse.ui.state.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AppRepository = AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun login(user: String, pass: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(3000L) // Simular llamada de red
            val loginResult = repository.login(user, pass)
            if (loginResult) {
                _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
            } else {
                // CORRECCIÓN: Usamos la propiedad 'error' que sí existe en LoginUiState
                _uiState.update { it.copy(isLoading = false, error = "Usuario o contraseña incorrectos") }
            }
        }
    }
}

