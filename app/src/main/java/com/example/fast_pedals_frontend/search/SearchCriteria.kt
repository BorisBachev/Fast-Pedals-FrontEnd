package com.example.fast_pedals_frontend.search

data class SearchCriteria(
    val title: String = "",
    val minPrice: Int = 0,
    val maxPrice: Int = 0,
    val location: String = "",
    val description: String = "",
    val type: String = "",
    val brand: String = "",
    val model: String = "",
    val size: String = "",
    val wheelSize: String = "",
    val frameMaterial: String = ""
)