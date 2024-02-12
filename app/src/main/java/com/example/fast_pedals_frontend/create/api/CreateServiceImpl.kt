package com.example.fast_pedals_frontend.create.api

import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import com.example.fast_pedals_frontend.create.api.request_response.CreateResponse
import retrofit2.Response

class CreateServiceImpl(
    private val createApi: CreateApi
): CreateService {

    override suspend fun createListing(
        createRequest: CreateRequest
    ): Response<CreateResponse> {
        return createApi.createListing(createRequest)
    }

}