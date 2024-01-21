package com.example.fast_pedals_frontend.app

import com.example.fast_pedals_frontend.auth.LoginRequest
import com.example.fast_pedals_frontend.auth.LoginResponse
import com.example.fast_pedals_frontend.auth.RegisterRequest
import com.example.fast_pedals_frontend.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("/api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}