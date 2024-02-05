package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.listing.ListingResponse

class BikeServiceImpl(
    private val bikeApi: BikeApi

): BikeService{
    override suspend fun getBike(bikeId: Long): BikeResponse {
        return bikeApi.getBike(bikeId)
    }

    override suspend fun getListing(listingId: Long): ListingResponse {
        return bikeApi.getListing(listingId)
    }

}