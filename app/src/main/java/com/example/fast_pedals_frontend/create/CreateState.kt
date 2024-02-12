package com.example.fast_pedals_frontend.create

sealed class CreateState {

    object None : CreateState()
    object Loading : CreateState()
    object Success : CreateState()
    data class Error(val message: String) : CreateState()
}