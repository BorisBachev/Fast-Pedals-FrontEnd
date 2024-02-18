package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.api.response.BikeResponse
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import retrofit2.Response

interface BikeService {

    suspend fun getBike(bikeId: Long): BikeResponse

    suspend fun getListing(listingId: Long): ListingResponse

    suspend fun favouriteCheck(listingId: Long): Response<Boolean>

    suspend fun favourite(listingId: Long): Response<Unit>

    suspend fun unFavourite(listingId: Long): Response<Unit>

    suspend fun deleteListing(listingId: Long): Response<Unit>

}