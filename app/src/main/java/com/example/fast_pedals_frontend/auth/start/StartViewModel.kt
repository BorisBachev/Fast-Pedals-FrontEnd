package com.example.fast_pedals_frontend.auth.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.firebase.FirebaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StartViewModel(

    private val authService: AuthService,
    private val firebaseViewModel: FirebaseViewModel

): ViewModel(){

    private val _startState = MutableStateFlow<StartState>(StartState.None)
    val startState: StateFlow<StartState> = _startState

    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged

    fun checkToken(){

        _startState.value = StartState.Loading

        viewModelScope.launch {

            var token = ""
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                } else {
                    _startState.value = StartState.Error("Failed to retrieve FCM token:${task.exception}")
                }
            }

            try {
                val response = authService.checkToken()
                if (response.code() == 403) {
                    _startState.value = StartState.Error("Token expired")
                    _isLogged.value = false
                } else {
                    _isLogged.value = response.isSuccessful
                    firebaseViewModel.updateToken(token)
                    _startState.value = StartState.Success
                }
            } catch (e: Exception) {
                _startState.value = StartState.Error("An error occurred")
                _isLogged.value = false
            }

        }

    }

    fun clearState(){
        _startState.value = StartState.None
    }

}