package com.example.fast_pedals_frontend.auth.login

sealed class LoginState {
    object None : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val errorMessage: String) : LoginState()
}
