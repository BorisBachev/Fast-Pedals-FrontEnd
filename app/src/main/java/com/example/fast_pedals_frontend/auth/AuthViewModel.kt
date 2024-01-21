package com.example.fast_pedals_frontend.auth

import androidx.lifecycle.ViewModel
import com.example.fast_pedals_frontend.app.RetrofitInstance
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val apiService = RetrofitInstance.api

    suspend fun register(
        name: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Response<RegisterResponse> {
        val request = RegisterRequest(name, email, password, fullName, phoneNumber)
        return apiService.register(request)
    }

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }
}