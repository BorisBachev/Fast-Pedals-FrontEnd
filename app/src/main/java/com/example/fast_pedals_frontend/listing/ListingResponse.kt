package com.example.fast_pedals_frontend.listing

data class ListingResponse (
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val location: String,
    val image: String,
    val bikeId: Long,
    val userId: Long
)