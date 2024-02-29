package com.example.fast_pedals_frontend.search

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
): ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Empty)
    val searchState: StateFlow<SearchState> = _searchState

    private val _isTypeDropdownExpanded = MutableStateFlow(false)
    val isTypeDropdownExpanded: StateFlow<Boolean> = _isTypeDropdownExpanded

    private val _isBrandDropdownExpanded = MutableStateFlow(false)
    val isBrandDropdownExpanded: StateFlow<Boolean> = _isBrandDropdownExpanded

    fun toggleTypeDropdown() {
        _isTypeDropdownExpanded.value = !_isTypeDropdownExpanded.value
        Log.d("SearchViewModel", "Type dropdown expanded: ${_isTypeDropdownExpanded.value}")
    }

    fun toggleBrandDropdown() {
        _isBrandDropdownExpanded.value = !_isBrandDropdownExpanded.value
        Log.d("SearchViewModel", "Brand dropdown expanded: ${_isBrandDropdownExpanded.value}")
    }

}