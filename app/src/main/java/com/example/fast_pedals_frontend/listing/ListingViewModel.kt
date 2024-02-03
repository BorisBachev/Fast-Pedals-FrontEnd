package com.example.fast_pedals_frontend.listing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListingViewModel(
    private val listingService: ListingService
): ViewModel() {

    private val _listingState = mutableStateOf<ListingState>(ListingState.None)
    val listingState: State<ListingState> = _listingState

    fun getPreviews(callback: (List<ListingResponse>?) -> Unit) {
        viewModelScope.launch {
            _listingState.value = ListingState.Loading
            val response = listingService.getPreviews()
            if (response.isSuccessful) {
                _listingState.value = ListingState.Success
                callback(response.body())
            } else {
                _listingState.value = ListingState.Error("An error occurred")
                callback(null)
            }
        }

    }

}