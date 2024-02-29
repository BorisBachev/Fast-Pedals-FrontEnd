package com.example.fast_pedals_frontend.firebase.api

interface FirebaseService {

    suspend fun updateToken(token: String)

}