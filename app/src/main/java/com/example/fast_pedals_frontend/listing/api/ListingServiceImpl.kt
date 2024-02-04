package com.example.fast_pedals_frontend.listing.api

import com.example.fast_pedals_frontend.listing.ListingResponse
import retrofit2.Response


class ListingServiceImpl(
    private val listingApi: ListingApi
): ListingService {
    override suspend fun getPreviews(): Response<List<ListingResponse>> {
        return listingApi.getPreviews()
    }
}