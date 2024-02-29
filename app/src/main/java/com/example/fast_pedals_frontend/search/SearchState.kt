package com.example.fast_pedals_frontend.search

sealed class SearchState {
    object Empty : SearchState()
    object Loading : SearchState()
    object Success : SearchState()
    data class Error(val message: String) : SearchState()
}