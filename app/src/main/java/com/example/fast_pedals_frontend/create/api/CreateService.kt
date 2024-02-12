package com.example.fast_pedals_frontend.create.api

import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import com.example.fast_pedals_frontend.create.api.request_response.CreateResponse
import retrofit2.Response

interface CreateService {

    suspend fun createListing(
        createRequest: CreateRequest
    ): Response<CreateResponse>

}