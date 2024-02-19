package com.example.fast_pedals_frontend.firebase.api

import com.example.fast_pedals_frontend.firebase.api.FirebaseDestinations.UPDATE
import retrofit2.http.Body
import retrofit2.http.PUT

interface FirebaseApi {

    @PUT(UPDATE)
    suspend fun updateToken(@Body token: String)

}