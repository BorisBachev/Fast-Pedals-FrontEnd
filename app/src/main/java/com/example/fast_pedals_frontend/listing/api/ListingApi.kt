package com.example.fast_pedals_frontend.listing.api

import retrofit2.Response
import retrofit2.http.GET

interface ListingApi {

    @GET(ListingDestinations.PREVIEW)
    suspend fun getPreviews(
    ): Response<List<ListingResponse>>

}
