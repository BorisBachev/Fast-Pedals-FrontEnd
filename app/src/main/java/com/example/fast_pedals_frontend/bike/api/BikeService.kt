package com.example.fast_pedals_frontend.bike.api

interface BikeService {

    suspend fun getBike(bikeId: Long): BikeResponse

}