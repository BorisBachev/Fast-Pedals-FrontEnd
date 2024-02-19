package com.example.fast_pedals_frontend.profile

sealed class ProfileState {

    object None : ProfileState()
    object Loading : ProfileState()
    object Success : ProfileState()

    data class Error(val message: String) : ProfileState()
}