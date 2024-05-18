package com.example.fast_pedals_frontend.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.firebase.FirebaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogInViewModel(

    private val authService: AuthService,
    private val firebaseViewModel: FirebaseViewModel

    ) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.None)
    val loginState: StateFlow<LoginState> = _loginState

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    fun login(
        email: String,
        password: String
    )
    {
        viewModelScope.launch {

            //if (!validateEmail(email)) return@launch
            if (!validatePassword(password)) return@launch

            _loginState.value = LoginState.Loading

            var token = ""
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                } else {
                    _loginState.value = LoginState.Error("Failed to retrieve FCM token: ${task.exception}")
                }
            }

            try {
                val response = authService.login(email, password)
                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success
                    firebaseViewModel.updateToken(token)
                } else {
                    when (response.code()) {
                        403 -> {
                            _loginState.value = LoginState.Error("Forbidden: Access denied")
                        }
                        404 -> {
                            _loginState.value = LoginState.Error("User not found")
                        }
                        500 -> {
                            _loginState.value = LoginState.Error("Internal server error: ${response.message()}")
                        }
                        else -> {
                            _loginState.value = LoginState.Error("Login failed: ${response.message()}")
                        }
                    }
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An error occurred")
            }

        }
    }

    fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
        return if (email.matches(emailRegex.toRegex())) {
            _emailError.value = null
            true
        } else {
            _emailError.value = "Please enter a valid email address"
            false
        }
    }

    fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            _passwordError.value = "Password cannot be empty"
            return false
        }
        _passwordError.value = null
        return true
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        //validateEmail(newEmail)
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        validatePassword(newPassword)
    }

}