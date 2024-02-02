package com.example.fast_pedals_frontend.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogInViewModel(

    private val authService: AuthService,


    ) : ViewModel() {

    private val _loginState = mutableStateOf<LoginState>(LoginState.None)
    val loginState: State<LoginState> = _loginState

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun login(
        email: String,
        password: String
    )
    {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val response = authService.login(email, password)
                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Log In failed")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An error occurred")
            }

        }
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

}