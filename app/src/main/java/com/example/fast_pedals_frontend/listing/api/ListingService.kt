package com.example.fast_pedals_frontend.listing.api

import com.example.fast_pedals_frontend.listing.ListingResponse
import retrofit2.Response

interface ListingService {

    suspend fun getPreviews(
    ): Response<List<ListingResponse>>

}