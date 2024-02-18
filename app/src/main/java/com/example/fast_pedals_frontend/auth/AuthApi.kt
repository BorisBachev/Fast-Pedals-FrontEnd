package com.example.fast_pedals_frontend.auth

import com.example.fast_pedals_frontend.auth.AuthDestinations.CHECK_TOKEN
import com.example.fast_pedals_frontend.auth.AuthDestinations.LOGIN
import com.example.fast_pedals_frontend.auth.AuthDestinations.REGISTER
import com.example.fast_pedals_frontend.auth.login.LoginRequest
import com.example.fast_pedals_frontend.auth.login.LoginResponse
import com.example.fast_pedals_frontend.auth.register.RegisterRequest
import com.example.fast_pedals_frontend.auth.register.RegisterResponse
import com.example.fast_pedals_frontend.auth.start.CheckResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthApi{

    @POST(REGISTER)
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>
    @POST(LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ) : Response<LoginResponse>

    @GET(CHECK_TOKEN)
    suspend fun checkToken(): Response<CheckResponse>

}