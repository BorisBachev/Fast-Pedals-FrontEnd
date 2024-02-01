package com.example.fast_pedals_frontend.auth

import com.example.fast_pedals_frontend.AuthSharedPreferences
import com.example.fast_pedals_frontend.auth.login.LoginRequest
import com.example.fast_pedals_frontend.auth.login.LoginResponse
import com.example.fast_pedals_frontend.auth.register.RegisterRequest
import com.example.fast_pedals_frontend.auth.register.RegisterResponse
import retrofit2.Response

class AuthServiceImpl(
    private val authApi: AuthApi,
    private val authPreferences: AuthSharedPreferences
) : AuthService {

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ): Response<RegisterResponse> {
        val request = RegisterRequest(name, email, password, fullName, phoneNumber)
        val registerResponse = authApi.register(request)
        if (registerResponse.isSuccessful) {
            authPreferences.saveJwtToken(registerResponse.body()!!.token)
        }
        return registerResponse
    }

    override suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        val loginResponse = authApi.login(request)
        if (loginResponse.isSuccessful) {
            authPreferences.saveJwtToken(loginResponse.body()!!.token)
        }
        return loginResponse
    }

}