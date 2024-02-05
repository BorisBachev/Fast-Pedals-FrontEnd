package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.BikeBrand
import com.example.fast_pedals_frontend.bike.BikeType

data class BikeResponse (
    val id: Long,
    val type: BikeType,
    val brand: BikeBrand,
    val model: String,
    val size: String,
    val wheelSize: Int,
    val frameMaterial: String
)
