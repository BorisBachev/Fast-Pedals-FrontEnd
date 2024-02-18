package com.example.fast_pedals_frontend.edit.api.request_response

data class EditResponse(
    val id: Long,
    val title: String,
    val minPrice: Double,
    val maxPrice: Double,
    val location: String,
    val description: String,
    val type: String,
    val brand: String,
    val model: String,
    val size: String,
    val wheelSize: Int,
    val frameMaterial: String
)
