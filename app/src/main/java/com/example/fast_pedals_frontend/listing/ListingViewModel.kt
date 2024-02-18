package com.example.fast_pedals_frontend.listing

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.listing.api.ListingService
import com.example.fast_pedals_frontend.listing.api.ListingState
import com.example.fast_pedals_frontend.search.SearchCriteria
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.search.api.SearchService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListingViewModel(
    private val listingService: ListingService,
    private val searchService: SearchService,
    private val sharedCriteriaViewModel: SharedCriteriaViewModel
): ViewModel() {

    private val _listingState = mutableStateOf<ListingState>(ListingState.None)
    val listingState: State<ListingState> = _listingState

    private val _searchCriteria = sharedCriteriaViewModel.searchCriteria
    val searchCriteria: StateFlow<SearchCriteria> = _searchCriteria

    private val _listings = MutableStateFlow<List<ListingResponse>?>(null)
    val listings: StateFlow<List<ListingResponse>?> = _listings

    fun search(searchCriteria: SearchCriteria) {
        viewModelScope.launch {
            _listingState.value = ListingState.Loading
            val response = searchService.search(searchCriteria)
            if (response.isSuccessful) {
                _listingState.value = ListingState.Success
                _listings.value = response.body()
            } else {
                _listingState.value = ListingState.Error("An error occurred")
                _listings.value = null
            }
        }
    }

    fun getFavourites() {
        viewModelScope.launch {
            _listingState.value = ListingState.Loading
            val response = listingService.getFavourites()
            if (response.isSuccessful) {
                _listingState.value = ListingState.Success
                _listings.value = response.body()
            } else {
                _listingState.value = ListingState.Error("An error occurred")
                _listings.value = null
            }
        }
    }

}