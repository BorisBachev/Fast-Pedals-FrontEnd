package com.example.fast_pedals_frontend.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(): ViewModel() {

    private val _searchCriteria = MutableStateFlow(SearchCriteria())
    val searchCriteria: StateFlow<SearchCriteria> get() = _searchCriteria

    fun updateSearchCriteria(newSearchCriteria: SearchCriteria) {
        _searchCriteria.value = newSearchCriteria
    }

    fun updateTitle(title: String) {
        _searchCriteria.value = _searchCriteria.value.copy(title = title)
    }

    fun updateMinPrice(minPrice: Int) {
        _searchCriteria.value = _searchCriteria.value.copy(minPrice = minPrice)
    }

    fun updateMaxPrice(maxPrice: Int) {
        _searchCriteria.value = _searchCriteria.value.copy(maxPrice = maxPrice)
    }

    fun updateLocation(location: String) {
        _searchCriteria.value = _searchCriteria.value.copy(location = location)
    }

    fun updateDescription(description: String) {
        _searchCriteria.value = _searchCriteria.value.copy(description = description)
    }

    fun updateType(type: String) {
        _searchCriteria.value = searchCriteria.value.copy(type = type)
    }

    fun updateBrand(brand: String) {
        _searchCriteria.value = searchCriteria.value.copy(brand = brand)
    }

    fun search(): SearchResponse {
        return SearchResponse("OK")
    }

}