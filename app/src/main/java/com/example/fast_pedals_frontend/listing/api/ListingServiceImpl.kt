package com.example.fast_pedals_frontend.listing.api

import retrofit2.Response


class ListingServiceImpl(
    private val listingApi: ListingApi
): ListingService {

    override suspend fun getFavourites(): Response<List<ListingResponse>> {
        return listingApi.getFavourites()
    }

}