package com.example.fast_pedals_frontend.listing

import com.example.fast_pedals_frontend.auth.AuthDestinations
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ListingApi {

    @GET(AuthDestinations.REGISTER)
    suspend fun getPreviews(
    ): Response<List<ListingResponse>>

}
