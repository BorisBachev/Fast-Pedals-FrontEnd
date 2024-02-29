package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.api.BikeDestinations.BIKE
import com.example.fast_pedals_frontend.bike.api.BikeDestinations.FAVOURITE
import com.example.fast_pedals_frontend.bike.api.BikeDestinations.FAVOURITE_CHECK
import com.example.fast_pedals_frontend.bike.api.BikeDestinations.FAVOURITE_DELETE
import com.example.fast_pedals_frontend.bike.api.BikeDestinations.LISTING
import com.example.fast_pedals_frontend.bike.api.response.BikeResponse
import com.example.fast_pedals_frontend.bike.api.response.WholeListingResponse
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BikeApi {

    @GET(BIKE)
    suspend fun getBike(
        @Path("bikeId") bikeId: Long
    ): BikeResponse

    @GET(LISTING)
    suspend fun getWholeListing(
        @Path("listingId") listingId: Long
    ): Response<WholeListingResponse>

    @POST(FAVOURITE_CHECK)
    suspend fun favouriteCheck(
        @Path("listingId") listingId: Long
    ): Response<Boolean>

    @POST(FAVOURITE)
    suspend fun favourite(
        @Path("listingId") listingId: Long
    ): Response<Unit>

    @DELETE(FAVOURITE_DELETE)
    suspend fun unFavourite(
        @Path("listingId") listingId: Long
    ): Response<Unit>

    @DELETE(LISTING)
    suspend fun deleteListing(
        @Path("listingId") listingId: Long
    ): Response<Unit>

}