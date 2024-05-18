package com.example.fast_pedals_frontend.create.api

import com.example.fast_pedals_frontend.create.api.CreateDestinations.LISTING
import com.example.fast_pedals_frontend.create.api.CreateDestinations.PRESIGNED_IMAGE
import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import com.example.fast_pedals_frontend.create.api.request_response.CreateResponse
import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.http.Headers

interface CreateApi {

    @POST(LISTING)
    suspend fun createListing(
        @Body createRequest: CreateRequest
    ): Response<CreateResponse>

    @POST(PRESIGNED_IMAGE)
    suspend fun generatePresignedUploadUrl(
        @Query("key") key: String,
        @Query("contentType") contentType: String
    ): Response<PresignedResponse>

    @PUT
    suspend fun uploadImage(
        @Url presignedUrl: String,
        @Body image: RequestBody
    ): Response<Unit>

}