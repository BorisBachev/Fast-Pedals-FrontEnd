package com.example.fast_pedals_frontend.auth.start

sealed class StartState {
    object None: StartState()
    object Loading: StartState()
    object Success: StartState()
    data class Error(val message: String): StartState()
}