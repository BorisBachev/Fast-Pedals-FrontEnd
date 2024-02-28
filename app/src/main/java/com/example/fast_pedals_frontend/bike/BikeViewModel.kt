package com.example.fast_pedals_frontend.bike

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.Constants.ERROR_MESSAGE
import com.example.fast_pedals_frontend.bike.api.BikeService
import com.example.fast_pedals_frontend.bike.api.response.WholeListingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BikeViewModel(
    private val bikeService: BikeService,

): ViewModel() {

    private val _bikeState = mutableStateOf<BikeState>(BikeState.None)
    val bikeState: State<BikeState> = _bikeState

    private val _wholeListing = MutableStateFlow<WholeListingResponse?>(null)
    val wholeListing: StateFlow<WholeListingResponse?> = _wholeListing

    fun getWholeListing(listingId: Long) {

        viewModelScope.launch {

            _bikeState.value = BikeState.Loading

            try {
                val response = bikeService.getWholeListing(listingId).body()
                _wholeListing.value = response
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

    fun deleteListing() {
        viewModelScope.launch {
            _bikeState.value = BikeState.Loading
            try {
                _wholeListing.value?.let { bikeService.deleteListing(it.id) }
                _bikeState.value = BikeState.Success
            } catch (e: Exception) {
                _bikeState.value = BikeState.Error(ERROR_MESSAGE)
            }
        }
    }

}