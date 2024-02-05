package com.example.fast_pedals_frontend.bike.api

class BikeServiceImpl(
    private val bikeApi: BikeApi

): BikeService{
    override suspend fun getBike(bikeId: Long): BikeResponse {
        return bikeApi.getBike(bikeId)
    }

}