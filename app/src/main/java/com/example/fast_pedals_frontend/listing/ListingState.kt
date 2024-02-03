package com.example.fast_pedals_frontend.listing

sealed class ListingState {
    object None : ListingState()
    object Loading : ListingState()
    object Success : ListingState()
    data class Error(val errorMessage: String) : ListingState()
}
