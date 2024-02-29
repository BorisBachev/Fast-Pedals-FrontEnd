package com.example.fast_pedals_frontend.bike

sealed class BikeState {
    object None : BikeState()
    object Loading : BikeState()
    object Success : BikeState()
    data class Error(val errorMessage: String) : BikeState()
}