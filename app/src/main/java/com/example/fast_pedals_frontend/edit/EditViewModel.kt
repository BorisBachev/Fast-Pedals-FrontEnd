package com.example.fast_pedals_frontend.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fast_pedals_frontend.edit.api.EditService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditViewModel(

): ViewModel(){

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