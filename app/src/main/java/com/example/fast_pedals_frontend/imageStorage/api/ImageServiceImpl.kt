package com.example.fast_pedals_frontend.imageStorage.api

import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import com.example.fast_pedals_frontend.imageStorage.api.ImageApi
import com.example.fast_pedals_frontend.imageStorage.api.ImageService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class ImageServiceImpl(
    private val imageApi: ImageApi
): ImageService {

    override suspend fun generatePresignedDownloadUrl(it: String): Response<PresignedResponse> {
        return imageApi.getImageLink(it)
    }

    override suspend fun downloadImage(imageUrl: String): Response<ResponseBody> {
        return imageApi.downloadImage(imageUrl)
    }

    override suspend fun generatePresignedUploadUrl(
        key: String,
        contentType: String
    ): Response<PresignedResponse> {
        return imageApi.generatePresignedUploadUrl(key, contentType)
    }

    override suspend fun uploadImage(
        presignedUrl: String,
        image: ByteArray,
        contentType: String
    ): Response<Unit> {
        val requestBody = image.toRequestBody(contentType.toMediaTypeOrNull())
        return imageApi.uploadImage(presignedUrl, requestBody)
    }

}