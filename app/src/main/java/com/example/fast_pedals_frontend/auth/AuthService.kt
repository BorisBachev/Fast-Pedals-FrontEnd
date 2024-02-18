package com.example.fast_pedals_frontend.auth

import com.example.fast_pedals_frontend.auth.login.LoginResponse
import com.example.fast_pedals_frontend.auth.register.RegisterResponse
import retrofit2.Response

interface AuthService {

    suspend fun register(
        name: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Response<RegisterResponse>

    suspend fun login(email: String, password: String): Response<LoginResponse>

}