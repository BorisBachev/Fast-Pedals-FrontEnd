package com.example.fast_pedals_frontend.listing

import retrofit2.Response


class ListingServiceImpl(
    private val listingApi: ListingApi
) {
    suspend fun getListings(): Response<ListingResponse> {
        return listingApi.getListings()
    }
}