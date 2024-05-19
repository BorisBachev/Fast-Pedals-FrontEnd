package com.example.fast_pedals_frontend.imageStorage.api

sealed class ImageState {
    object None : ImageState()
    object Loading : ImageState()
    object Success : ImageState()
    data class Error(val errorMessage: String) : ImageState()
}