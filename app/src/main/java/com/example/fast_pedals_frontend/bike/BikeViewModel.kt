package com.example.fast_pedals_frontend.bike

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.api.BikeResponse
import com.example.fast_pedals_frontend.bike.api.BikeService
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeViewModel(
    private val bikeService: BikeService

): ViewModel() {

    private val _bikeState = mutableStateOf<BikeState>(BikeState.None)
    val bikeState: State<BikeState> = _bikeState

    private val _listing = MutableStateFlow<ListingResponse?>(null)
    val listing: StateFlow<ListingResponse?> = _listing

    private val _bike = MutableStateFlow<BikeResponse?>(null)
    val bike: StateFlow<BikeResponse?> = _bike

    private val _contactInfo = MutableStateFlow<ContactInfo?>(null)
    val contactInfo: StateFlow<ContactInfo?> = _contactInfo

    private val _isFavourite = MutableStateFlow<Boolean?>(null)
    val isFavourite: StateFlow<Boolean?> = _isFavourite

    fun getBike(bikeId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.getBike(bikeId)
                _bike.value = response
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error("An error occurred")
            }

        }

    }

    fun getListing(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.getListing(listingId)
                _listing.value = response
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error("An error occurred")
            }

        }

    }

    fun getContactInfo(userId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.getContactInfo(userId)
                _contactInfo.value = response
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error("An error occurred")
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
                _bikeState.value = BikeState.Error("An error occurred")
            }
        }
    }

    fun favourite(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.favourite(listingId)
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error("An error occurred")
            }

        }

    }

    fun unFavourite(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.unFavourite(listingId)
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error("An error occurred")
            }

        }

    }

    fun toggleFavourite() {
        _isFavourite.value = !_isFavourite.value!!
    }

}