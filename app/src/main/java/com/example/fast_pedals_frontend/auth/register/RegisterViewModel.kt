package com.example.fast_pedals_frontend.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.AuthService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(

    private val authService: AuthService,

    ): ViewModel() {

    private val _registerState = mutableStateOf<RegisterState>(RegisterState.None)
    val registerState: State<RegisterState> = _registerState

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber


    fun register(
        name: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String)
    {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading

            var token: String
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                    println("FCM Token: $token")
                } else {
                    println("Failed to retrieve FCM token: ${task.exception}")
                }
            }

            try {
                val response = authService.register(name, email, password, fullName, phoneNumber)
                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success
                } else {
                    _registerState.value = RegisterState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("An error occurred")
            }

        }
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateFullName(newFullName: String) {
        _fullName.value = newFullName
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

}
