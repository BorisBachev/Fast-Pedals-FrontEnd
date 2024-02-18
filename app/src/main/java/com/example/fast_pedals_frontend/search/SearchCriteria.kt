package com.example.fast_pedals_frontend.search

import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType

data class SearchCriteria(

    val title: String,
    val minPrice: Double,
    val maxPrice: Double,
    val location: String,
    val description: String,
    val type: BikeType?,
    val brand: BikeBrand?,
    val model: String,
    val size: String,
    val wheelSize: Int?,
    val frameMaterial: String

)