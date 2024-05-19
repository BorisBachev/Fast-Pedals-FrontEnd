package com.example.fast_pedals_frontend.imageStorage.api

import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import com.example.fast_pedals_frontend.imageStorage.api.ImageDestinations.PRESIGNED_DOWNLOAD
import com.example.fast_pedals_frontend.imageStorage.api.ImageDestinations.PRESIGNED_IMAGE
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url

interface ImageApi {

    @POST(PRESIGNED_DOWNLOAD)
    suspend fun getImageLink(
        @Query("key") key: String
    ): Response<PresignedResponse>

    @GET
    suspend fun downloadImage(
        @Url imageUrl: String
    ): Response<ResponseBody>

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