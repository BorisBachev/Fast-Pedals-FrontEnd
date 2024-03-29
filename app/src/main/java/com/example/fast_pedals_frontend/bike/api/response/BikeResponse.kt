package com.example.fast_pedals_frontend.bike.api.response

import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType

data class BikeResponse (

    val id: Long,
    val type: BikeType,
    val brand: BikeBrand,
    val model: String,
    val size: String,
    val wheelSize: Int,
    val frameMaterial: String


)
