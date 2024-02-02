package com.example.fast_pedals_frontend.search

data class SearchListing (

    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val bikeId: Long

)
