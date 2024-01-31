package com.example.fast_pedals_frontend.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAuth {
    private const val BASE_URL = "http://10.0.2.2:8080/api/auth/"
        //"http://localhost:8080/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}