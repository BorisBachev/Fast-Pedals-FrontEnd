package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.api.response.BikeResponse
import com.example.fast_pedals_frontend.bike.api.response.WholeListingResponse
import retrofit2.Response

class BikeServiceImpl(
    private val bikeApi: BikeApi

): BikeService{
    override suspend fun getBike(bikeId: Long): BikeResponse {
        return bikeApi.getBike(bikeId)
    }

    override suspend fun getWholeListing(listingId: Long): Response<WholeListingResponse> {
        return bikeApi.getWholeListing(listingId)
    }

    override suspend fun favouriteCheck(listingId: Long): Response<Boolean> {

        return bikeApi.favouriteCheck(listingId)

    }

    override suspend fun favourite(listingId: Long): Response<Unit> {

        return bikeApi.favourite(listingId)

    }

    override suspend fun unFavourite(listingId: Long): Response<Unit> {

        return bikeApi.unFavourite(listingId)

    }

    override suspend fun deleteListing(listingId: Long): Response<Unit> {
        return bikeApi.deleteListing(listingId)
    }

}