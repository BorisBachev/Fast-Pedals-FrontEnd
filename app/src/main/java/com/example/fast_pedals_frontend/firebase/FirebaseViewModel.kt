package com.example.fast_pedals_frontend.firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.firebase.api.FirebaseService
import kotlinx.coroutines.launch

class FirebaseViewModel(
    private val firebaseService: FirebaseService
): ViewModel() {

        fun updateToken(token: String) {
            viewModelScope.launch {
                firebaseService.updateToken(token)
            }
        }
}