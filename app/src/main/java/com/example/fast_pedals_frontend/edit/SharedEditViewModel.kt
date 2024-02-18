package com.example.fast_pedals_frontend.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.api.response.BikeResponse
import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import com.example.fast_pedals_frontend.edit.api.EditService
import com.example.fast_pedals_frontend.edit.api.request_response.EditRequest
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class SharedEditViewModel(
    private val editService: EditService
): ViewModel() {

    private val _editRequest = MutableStateFlow(
        EditRequest(0,"", "", 0.0, "", date = LocalDate.now().toString() , listOf("", ""), BikeType.OTHER, BikeBrand.OTHER, "", "", 29, "", 0)
    )
    val editRequest: StateFlow<EditRequest> get() = _editRequest

    private val _editState = MutableStateFlow<EditState>(EditState.None)
    val editState: StateFlow<EditState> = _editState

    fun setEditRequest(listingId: Long, bike: BikeResponse, listing: ListingResponse) {

        val editRequest = EditRequest(
            listingId,
            listing.title,
            listing.description,
            listing.price,
            listing.location,
            date = LocalDate.now().toString(),
            listOf("", ""),
            bike.type,
            bike.brand,
            bike.model,
            bike.size,
            bike.wheelSize,
            bike.frameMaterial,
            bike.id
        )

        _editRequest.value = editRequest

    }

    fun edit() {

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


    fun updateEditRequest(newEditRequest: EditRequest) {
        _editRequest.value = newEditRequest
    }

    fun updateTitle(title: String) {
        _editRequest.value = _editRequest.value.copy(title = title)
    }

    fun updatePrice(price: Double?) {
        _editRequest.value = _editRequest.value.copy(price = price)
    }

    fun updateLocation(location: String) {
        _editRequest.value = _editRequest.value.copy(location = location)
    }

    fun updateDescription(description: String) {
        _editRequest.value = _editRequest.value.copy(description = description)
    }

    fun updateType(type: BikeType) {
        _editRequest.value = _editRequest.value.copy(type = type)
    }

    fun updateBrand(brand: BikeBrand) {
        _editRequest.value = _editRequest.value.copy(brand = brand)
    }

    fun updateModel(model: String) {
        _editRequest.value = _editRequest.value.copy(model = model)
    }

    fun updateSize(size: String) {
        _editRequest.value = _editRequest.value.copy(size = size)
    }

    fun updateWheelSize(wheelSize: Int?) {
        _editRequest.value = _editRequest.value.copy(wheelSize = wheelSize)
    }

    fun updateFrameMaterial(frameMaterial: String) {
        _editRequest.value = _editRequest.value.copy(frameMaterial = frameMaterial)
    }

    fun resetEditRequest() {
        _editRequest.value = EditRequest(0, "", "", 0.0, "", date = LocalDate.now().toString() , listOf("", ""), BikeType.OTHER, BikeBrand.OTHER, "", "", 29, "", 0)
    }

}