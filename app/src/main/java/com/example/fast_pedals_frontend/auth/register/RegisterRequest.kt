package com.example.fast_pedals_frontend.auth.register

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String
)