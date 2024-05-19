package com.example.fast_pedals_frontend.create.api

import com.example.fast_pedals_frontend.create.api.CreateDestinations.LISTING
import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import com.example.fast_pedals_frontend.create.api.request_response.CreateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateApi {

    @POST(LISTING)
    suspend fun createListing(
        @Body createRequest: CreateRequest
    ): Response<CreateResponse>

}