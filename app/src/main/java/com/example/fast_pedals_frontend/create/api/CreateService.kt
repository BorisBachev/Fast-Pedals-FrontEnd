package com.example.fast_pedals_frontend.create.api

import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import com.example.fast_pedals_frontend.create.api.request_response.CreateResponse
import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface CreateService {

    suspend fun createListing(
        createRequest: CreateRequest
    ): Response<CreateResponse>

    suspend fun generatePresignedUploadUrl(
        key: String,
        contentType: String
    ): Response<PresignedResponse>

    suspend fun uploadImage(
        presignedUrl: String,
        image: ByteArray,
        contentType: String
    ): Response<Unit>

}