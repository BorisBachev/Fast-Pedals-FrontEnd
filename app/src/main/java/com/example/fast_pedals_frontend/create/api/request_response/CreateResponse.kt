package com.example.fast_pedals_frontend.create.api.request_response

data class CreateResponse (

    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val location: String,
    val date: String,
    val images: List<String>,
    val bikeId: Long,
    val userId: Long

)