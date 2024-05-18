package com.example.fast_pedals_frontend.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.firebase.FirebaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(

    private val authService: AuthService,
    private val firebaseViewModel: FirebaseViewModel

    ): ViewModel() {

    private val _registerState = mutableStateOf<RegisterState>(RegisterState.None)
    val registerState: State<RegisterState> = _registerState

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    private val _fullNameError = MutableStateFlow<String?>(null)
    val fullNameError: StateFlow<String?> = _fullNameError

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _phoneNumberError = MutableStateFlow<String?>(null)
    val phoneNumberError: StateFlow<String?> = _phoneNumberError


    fun register(
        name: String,
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String)
    {
        viewModelScope.launch {

            if (!validateEmail(email)) return@launch
            if (!validatePassword(password)) return@launch
            if (!validateFullName(fullName)) return@launch
            if (!validatePhoneNumber(phoneNumber)) return@launch

            _registerState.value = RegisterState.Loading

            var token = ""
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                } else {
                    _registerState.value =
                        RegisterState.Error("Failed to retrieve FCM token: ${task.exception}")
                }
            }

            try {
                val response = authService.register(name, email, password, fullName, phoneNumber)
                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success
                    firebaseViewModel.updateToken(token)
                } else {
                    when(response.code()) {
                        403 -> {
                            _registerState.value = RegisterState.Error("Forbidden: Access denied")
                        }
                        409 -> {
                            _registerState.value = RegisterState.Error("User already exists")
                        }
                        500 -> {
                            _registerState.value = RegisterState.Error("Internal server error")
                        }
                        else -> {
                            _registerState.value = RegisterState.Error("An error occurred")
                        }
                    }
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("An error occurred")
            }
        }

    }

    private fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
        return if (email.matches(emailRegex.toRegex())) {
            _emailError.value = null
            true
        } else {
            _emailError.value = "Please enter a valid email address"
            false
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.length >= 6) {
            _passwordError.value = null
            true
        } else {
            _passwordError.value = "Password must be at least 6 characters long"
            false
        }
    }

    private fun validateFullName(fullName: String): Boolean {
        return if (fullName.split(" ").size >= 2) {
            _fullNameError.value = null
            true
        } else {
            _fullNameError.value = "Please enter at least two names for full name"
            false
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = "^\\d{6,14}\$"
        return if (phoneNumber.matches(phoneRegex.toRegex())) {
            _phoneNumberError.value = null
            true
        } else {
            _phoneNumberError.value = "Please enter a valid phone number"
            false
        }
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        validateEmail(newEmail)
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        validatePassword(newPassword)
    }

    fun updateFullName(newFullName: String) {
        _fullName.value = newFullName
        validateFullName(newFullName)
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
        validatePhoneNumber(newPhoneNumber)
    }

}
