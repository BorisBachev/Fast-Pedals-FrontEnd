package com.example.fast_pedals_frontend.listing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.listing.api.ListingService
import com.example.fast_pedals_frontend.listing.api.ListingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListingViewModel(
    private val listingService: ListingService
): ViewModel() {

    private val _listingState = mutableStateOf<ListingState>(ListingState.None)
    val listingState: State<ListingState> = _listingState

    private val _previews = MutableStateFlow<List<ListingResponse>?>(null)
    val previews: StateFlow<List<ListingResponse>?> = _previews

    fun getPreviews() {
        viewModelScope.launch {
            _listingState.value = ListingState.Loading
            val response = listingService.getPreviews()
            if (response.isSuccessful) {
                _listingState.value = ListingState.Success
                 _previews.value = response.body()
            } else {
                _listingState.value = ListingState.Error("An error occurred")
                _previews.value = null
            }
        }

    }

}