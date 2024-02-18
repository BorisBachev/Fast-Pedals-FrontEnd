package com.example.fast_pedals_frontend.listing.api

import retrofit2.Response

interface ListingService {

    suspend fun getFavourites(): Response<List<ListingResponse>>

}