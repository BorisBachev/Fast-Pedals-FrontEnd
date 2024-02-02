package com.example.fast_pedals_frontend.listing

import retrofit2.Response

interface ListingService {

    suspend fun getListings(
    ): Response<ListingResponse>

}