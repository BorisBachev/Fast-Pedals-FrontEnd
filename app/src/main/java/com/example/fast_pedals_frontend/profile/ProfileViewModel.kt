package com.example.fast_pedals_frontend.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.AuthSharedPreferences
import com.example.fast_pedals_frontend.bike.Constants
import com.example.fast_pedals_frontend.listing.api.FavouriteRequest
import com.example.fast_pedals_frontend.profile.api.ProfileService
import com.example.fast_pedals_frontend.profile.api.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(

    private val profileService: ProfileService,
    private val authSharedPreferences: AuthSharedPreferences

): ViewModel(){

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.None)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _userInfo = MutableStateFlow<UserResponse?>(null)
    val userInfo: StateFlow<UserResponse?> = _userInfo

    fun getUserByEmail() {

        viewModelScope.launch {

            _profileState.value = ProfileState.Loading

            try {
                val response = profileService.getUserByEmail().body()
                _userInfo.value = response
                _profileState.value = ProfileState.Success
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(Constants.ERROR_MESSAGE)
            }

        }

    }

    fun logout() {

        viewModelScope.launch {

            try {
                profileService.logout()
                _profileState.value = ProfileState.Success
                authSharedPreferences.clearJwtToken()
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(Constants.ERROR_MESSAGE)
            }

        }

    }

}