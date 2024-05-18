package com.example.fast_pedals_frontend.listing.api

import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface ListingService {

    suspend fun getFavourites(): Response<List<ListingResponse>>
    suspend fun generatePresignedDownloadUrl(it: String): Response<PresignedResponse>

    suspend fun downloadImage(imageUrl: String): Response<ResponseBody>

}