package com.example.fast_pedals_frontend.auth.login

import androidx.lifecycle.ViewModel
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.auth.register.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

class LogInViewModel(

    private val authService: AuthService,


    ) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    suspend fun login(
        email: String,
        password: String
    ): Response<LoginResponse>
    {
        return authService.login(email, password)
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

}