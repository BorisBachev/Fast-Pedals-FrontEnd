package com.example.fast_pedals_frontend.bike

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.api.BikeResponse
import com.example.fast_pedals_frontend.bike.api.BikeService
import com.example.fast_pedals_frontend.listing.ListingResponse
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

}