package com.example.fast_pedals_frontend.imageStorage.api

import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface ImageService {

    suspend fun generatePresignedDownloadUrl(it: String): Response<PresignedResponse>

    suspend fun downloadImage(imageUrl: String): Response<ResponseBody>

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