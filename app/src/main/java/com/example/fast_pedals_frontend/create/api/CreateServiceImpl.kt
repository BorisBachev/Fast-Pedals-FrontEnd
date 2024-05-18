package com.example.fast_pedals_frontend.create.api

import android.util.Log
import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import com.example.fast_pedals_frontend.create.api.request_response.CreateResponse
import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class CreateServiceImpl(
    private val createApi: CreateApi
): CreateService {

    override suspend fun createListing(
        createRequest: CreateRequest
    ): Response<CreateResponse> {
        return createApi.createListing(createRequest)
    }

    override suspend fun generatePresignedUploadUrl(
        key: String,
        contentType: String
    ): Response<PresignedResponse> {
        return createApi.generatePresignedUploadUrl(key, contentType)
    }

    override suspend fun uploadImage(
        presignedUrl: String,
        image: ByteArray,
        contentType: String
    ): Response<Unit> {
        val requestBody = image.toRequestBody(contentType.toMediaTypeOrNull())
        return createApi.uploadImage(presignedUrl, requestBody)
    }

}