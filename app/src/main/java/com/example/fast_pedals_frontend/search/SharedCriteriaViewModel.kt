package com.example.fast_pedals_frontend.search

import androidx.lifecycle.ViewModel
import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedCriteriaViewModel(): ViewModel() {

    private val _searchCriteria = MutableStateFlow(SearchCriteria("", 0.0, 10000.0, "", "",
        null, null, "", "", wheelSize = null, "", userId = null))
    val searchCriteria: StateFlow<SearchCriteria> get() = _searchCriteria

    private val _isSearch = MutableStateFlow(false)
    val isSearch: StateFlow<Boolean> get() = _isSearch

    fun updateIsSearch(isSearch: Boolean) {
        _isSearch.value = isSearch
    }

    fun updateSearchCriteria(newSearchCriteria: SearchCriteria) {
        _searchCriteria.value = newSearchCriteria
    }

    fun updateTitle(title: String) {
        _searchCriteria.value = _searchCriteria.value.copy(title = title)
    }

    fun updateMinPrice(minPrice: Double?) {
        _searchCriteria.value = _searchCriteria.value.copy(minPrice = minPrice)
    }

    fun updateMaxPrice(maxPrice: Double?) {
        _searchCriteria.value = _searchCriteria.value.copy(maxPrice = maxPrice)
    }

    fun updateLocation(location: String) {
        _searchCriteria.value = _searchCriteria.value.copy(location = location)
    }

    fun updateDescription(description: String) {
        _searchCriteria.value = _searchCriteria.value.copy(description = description)
    }

    fun updateType(type: BikeType?) {
        _searchCriteria.value = searchCriteria.value.copy(type = type)
    }

    fun updateBrand(brand: BikeBrand?) {
        _searchCriteria.value = searchCriteria.value.copy(brand = brand)
    }

    fun updateModel(model: String) {
        _searchCriteria.value = searchCriteria.value.copy(model = model)
    }

    fun updateSize(size: String) {
        _searchCriteria.value = searchCriteria.value.copy(size = size)
    }

    fun updateWheelSize(wheelSize: Int?) {
        _searchCriteria.value = searchCriteria.value.copy(wheelSize = wheelSize)
    }

    fun updateFrameMaterial(frameMaterial: String) {
        _searchCriteria.value = searchCriteria.value.copy(frameMaterial = frameMaterial)
    }

    fun updateUserId(userId: Long?) {
        _searchCriteria.value = searchCriteria.value.copy(userId = userId)
    }

    fun resetSearchCriteria() {
        _searchCriteria.value = SearchCriteria("", 0.0, 10000.0, "", "",
            null, null, "", "", null, "", null)
    }


}