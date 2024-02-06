package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.ContactInfo
import com.example.fast_pedals_frontend.bike.api.BikeDestination.BIKE
import com.example.fast_pedals_frontend.bike.api.BikeDestination.CONTACT_INFO
import com.example.fast_pedals_frontend.bike.api.BikeDestination.LISTING
import com.example.fast_pedals_frontend.listing.ListingResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BikeApi {

    @GET(BIKE)
    suspend fun getBike(
        @Path("bikeId") bikeId: Long
    ): BikeResponse

    @GET(LISTING)
    suspend fun getListing(
        @Path("listingId") listingId: Long
    ): ListingResponse

    @GET(CONTACT_INFO)
    suspend fun getContactInfo(
        @Path("userId") userId: Long
    ): ContactInfo

}