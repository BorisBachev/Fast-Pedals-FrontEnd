package com.example.fast_pedals_frontend.bike

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.Constants.ERROR_MESSAGE
import com.example.fast_pedals_frontend.bike.api.response.BikeResponse
import com.example.fast_pedals_frontend.bike.api.BikeService
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.profile.api.ProfileService
import com.example.fast_pedals_frontend.profile.api.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeViewModel(
    private val bikeService: BikeService,
    private val profileService: ProfileService

): ViewModel() {

    private val _bikeState = mutableStateOf<BikeState>(BikeState.None)
    val bikeState: State<BikeState> = _bikeState

    private val _listing = MutableStateFlow<ListingResponse?>(null)
    val listing: StateFlow<ListingResponse?> = _listing

    private val _bike = MutableStateFlow<BikeResponse?>(null)
    val bike: StateFlow<BikeResponse?> = _bike

    private val _ownerInfo = MutableStateFlow<UserResponse?>(null)
    val ownerInfo: StateFlow<UserResponse?> = _ownerInfo

    private val _userInfo = MutableStateFlow<UserResponse?>(null)
    val userInfo: StateFlow<UserResponse?> = _userInfo

    private val _isFavourite = MutableStateFlow<Boolean?>(null)
    val isFavourite: StateFlow<Boolean?> = _isFavourite

    private val _isFromUser = MutableStateFlow<Boolean?>(null)
    val isFromUser: StateFlow<Boolean?> = _isFromUser

    fun getBike(bikeId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.getBike(bikeId)
                _bike.value = response
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }

        }

    }

    fun getListing(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.getListing(listingId)
                _listing.emit(response)
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }

        }

    }

    fun isFavourite(listingId: Long) {
        viewModelScope.launch {
            _bikeState.value = BikeState.Loading
            try {
                val response = bikeService.favouriteCheck(listingId)
                _isFavourite.value = response.body()
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }
        }
    }

    fun favourite(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                bikeService.favourite(listingId)
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }

        }

    }

    fun unFavourite(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                bikeService.unFavourite(listingId)
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }

        }

    }

    fun toggleFavourite() {
        _isFavourite.value = !_isFavourite.value!!
    }

    fun getUserOwner(id: Long) {
        viewModelScope.launch {
            _bikeState.value = BikeState.Loading
            try {
                val response = profileService.getUser(id).body()
                _ownerInfo.value = response
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }
        }
    }

    fun getUserInfo() {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = profileService.getUserByEmail().body()
                _userInfo.value = response
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }

        }

    }

    fun isFromUser() {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = profileService.getUserByEmail().body()!!
                if(response.id == _listing.value?.userId && response.name == _userInfo.value?.name && response.phoneNumber == _userInfo.value?.phoneNumber) {
                    _isFromUser.value = true
                }else {
                    _isFromUser.value = false
                }
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }

        }

    }

    fun deleteListing() {
        viewModelScope.launch {
            _bikeState.value = BikeState.Loading
            try {
                _listing.value?.let { bikeService.deleteListing(it.id) }
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }
        }
    }

}