package com.example.fast_pedals_frontend.firebase.api

class FirebaseServiceImpl(

    private val firebaseApi: FirebaseApi

): FirebaseService {

    override suspend fun updateToken(token: String) {
        firebaseApi.updateToken(token)
    }

}