package com.example.fast_pedals_frontend.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.edit.api.EditService
import com.example.fast_pedals_frontend.edit.api.request_response.EditRequest
import com.example.fast_pedals_frontend.imageStorage.ImageViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class EditViewModel(

    private val editService: EditService,
    private val imageViewModel: ImageViewModel

): ViewModel(){

    private val _editState = MutableStateFlow<EditState>(EditState.None)
    val editState: StateFlow<EditState> = _editState

    private val _isTypeDropdownExpanded = MutableStateFlow(false)
    val isTypeDropdownExpanded: StateFlow<Boolean> = _isTypeDropdownExpanded

    private val _isBrandDropdownExpanded = MutableStateFlow(false)
    val isBrandDropdownExpanded: StateFlow<Boolean> = _isBrandDropdownExpanded

    private val _isEditingImages = MutableStateFlow(false)
    val isEditingImages: StateFlow<Boolean> = _isEditingImages

    fun toggleTypeDropdown() {
        _isTypeDropdownExpanded.value = !_isTypeDropdownExpanded.value
    }

    fun toggleBrandDropdown() {
        _isBrandDropdownExpanded.value = !_isBrandDropdownExpanded.value
    }

    fun toggleEditingImages() {
        _isEditingImages.value = !_isEditingImages.value
    }

    fun edit(edit: EditRequest, files: List<File>, onEdit: (Long) -> Unit) {

        viewModelScope.launch {
            _editState.value = EditState.Loading

            try {
                val fileNames = mutableListOf<String>()

                files.forEach { file ->
                    val key = "${file.name}-${imageViewModel.generateRandomString()}.${file.extension}"
                    fileNames.add(key)
                }

                val success = imageViewModel.uploadImages(files, fileNames)

                if (success) {
                    val editRequest = edit.copy(images = fileNames)
                    val response = editService.editListing(editRequest)

                    if (response.isSuccessful) {
                        _editState.value = EditState.Success
                        response.body()?.id?.let { onEdit(it) }
                    } else {
                        _editState.value = EditState.Error(response.message())
                    }
                } else {
                    _editState.value = EditState.Error("Failed to upload one or more images")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _editState.value = EditState.Error("Failed to edit listing: ${e.message}")
            }
        }

    }
    
}