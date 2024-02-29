package com.example.fast_pedals_frontend.edit

sealed class EditState {

    object None : EditState()
    object Loading : EditState()

    object Success : EditState()

    data class Error(val message: String) : EditState()

}