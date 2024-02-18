package com.example.fast_pedals_frontend.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.firebase.FirebaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class LogInViewModel(

    private val authService: AuthService,
    private val firebaseViewModel: FirebaseViewModel

    ) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.None)
    val loginState: StateFlow<LoginState> = _loginState

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

            var token = ""
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                    println("FCM Token: $token")
                } else {
                    println("Failed to retrieve FCM token: ${task.exception}")
                }
            }

            try {
                val response = authService.login(email, password)
                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success
                    firebaseViewModel.updateToken(token)
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