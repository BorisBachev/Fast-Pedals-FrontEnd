package com.example.fast_pedals_frontend.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.edit.api.request_response.EditRequest
import com.example.fast_pedals_frontend.edit.api.EditService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditViewModel(

    private val editService: EditService,
    private val sharedEditViewModel: SharedEditViewModel

): ViewModel(){

    private val _editState = MutableStateFlow<EditState>(EditState.None)
    val editState: StateFlow<EditState> = _editState

    private val _isTypeDropdownExpanded = MutableStateFlow(false)
    val isTypeDropdownExpanded: StateFlow<Boolean> = _isTypeDropdownExpanded

    private val _isBrandDropdownExpanded = MutableStateFlow(false)
    val isBrandDropdownExpanded: StateFlow<Boolean> = _isBrandDropdownExpanded

    private val _editRequest = sharedEditViewModel.editRequest
    val editRequest: StateFlow<EditRequest> = _editRequest

    fun edit() {

        Log.d("EditViewModel", "Edit request: ${_editRequest.value}")

        viewModelScope.launch {

            var editRequest = _editRequest.value
            editRequest = editRequest.copy(bikeId = editRequest.id)

            val response = editService.editListing(editRequest)

            if (response.isSuccessful) {
                _editState.value = EditState.Success
            } else {
                _editState.value = EditState.Error(response.message())
            }

        }
    }

    fun toggleTypeDropdown() {
        _isTypeDropdownExpanded.value = !_isTypeDropdownExpanded.value
        Log.d("SearchViewModel", "Type dropdown expanded: ${_isTypeDropdownExpanded.value}")
    }

    fun toggleBrandDropdown() {
        _isBrandDropdownExpanded.value = !_isBrandDropdownExpanded.value
        Log.d("SearchViewModel", "Brand dropdown expanded: ${_isBrandDropdownExpanded.value}")
    }
    
}