package com.example.fast_pedals_frontend.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("/api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}