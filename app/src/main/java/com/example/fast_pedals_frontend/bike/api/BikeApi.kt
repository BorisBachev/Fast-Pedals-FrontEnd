package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.api.BikeDestination.BIKE
import retrofit2.http.GET
import retrofit2.http.Path

interface BikeApi {

    @GET(BIKE)
    suspend fun getBike(
        @Path("bikeId") bikeId: Long
    ): BikeResponse

}