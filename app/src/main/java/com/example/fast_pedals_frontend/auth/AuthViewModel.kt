package com.example.fast_pedals_frontend.auth

import androidx.lifecycle.ViewModel
import com.example.fast_pedals_frontend.auth.login.LoginResponse
import com.example.fast_pedals_frontend.auth.register.RegisterResponse
import retrofit2.Response

class AuthViewModel(

    private val authService: AuthService

) : ViewModel() {

    suspend fun register(
        name: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Response<RegisterResponse>
    {
        return authService.register(
            name,
            email,
            password,
            fullName,
            phoneNumber)
    }

    suspend fun login(
        email: String,
        password: String
    ): Response<LoginResponse>
    {
        return authService.login(email, password)
    }
}