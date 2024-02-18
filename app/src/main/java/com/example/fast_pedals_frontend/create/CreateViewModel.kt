package com.example.fast_pedals_frontend.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import com.example.fast_pedals_frontend.create.api.CreateService
import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateViewModel(

    private val createService: CreateService

): ViewModel() {

    private val _createState = MutableStateFlow<CreateState>(CreateState.None)
    val createState: StateFlow<CreateState> = _createState

    private val _isTypeDropdownExpanded = MutableStateFlow(false)
    val isTypeDropdownExpanded: StateFlow<Boolean> = _isTypeDropdownExpanded

    private val _isBrandDropdownExpanded = MutableStateFlow(false)
    val isBrandDropdownExpanded: StateFlow<Boolean> = _isBrandDropdownExpanded

    private val _createRequest = MutableStateFlow(
        CreateRequest("", "", 0.0, "", date = LocalDate.now().toString() , listOf("", ""), BikeType.OTHER, BikeBrand.OTHER, "", "", 29, "")
    )
    val createRequest: StateFlow<CreateRequest> get() = _createRequest

    private val _listingId = MutableStateFlow<Long?>(null)
    val listingId: StateFlow<Long?> = _listingId

    fun create() {
        viewModelScope.launch {

            val createRequest = _createRequest.value

            val response = createService.createListing(createRequest)

            if (response.isSuccessful) {
                _listingId.value =  response.body()?.id
                _createState.value = CreateState.Success
            } else {
                _createState.value = CreateState.Error(response.message())
            }

        }
    }

    fun updateCreateRequest(newCreateRequest: CreateRequest) {
        _createRequest.value = newCreateRequest
    }

    fun updateTitle(title: String) {
        _createRequest.value = _createRequest.value.copy(title = title)
    }

    fun updatePrice(price: Double?) {
        _createRequest.value = _createRequest.value.copy(price = price)
    }

    fun updateLocation(location: String) {
        _createRequest.value = _createRequest.value.copy(location = location)
    }

    fun updateDescription(description: String) {
        _createRequest.value = _createRequest.value.copy(description = description)
    }

    fun updateType(type: BikeType) {
        _createRequest.value = _createRequest.value.copy(type = type)
    }

    fun updateBrand(brand: BikeBrand) {
        _createRequest.value = _createRequest.value.copy(brand = brand)
    }

    fun updateModel(model: String) {
        _createRequest.value = _createRequest.value.copy(model = model)
    }

    fun updateSize(size: String) {
        _createRequest.value = _createRequest.value.copy(size = size)
    }

    fun updateWheelSize(wheelSize: Int?) {
        _createRequest.value = _createRequest.value.copy(wheelSize = wheelSize)
    }

    fun updateFrameMaterial(frameMaterial: String) {
        _createRequest.value = _createRequest.value.copy(frameMaterial = frameMaterial)
    }

    fun resetCreateRequest() {
        _createRequest.value = CreateRequest("", "", 0.0, "", date = LocalDate.now().toString() , listOf("", ""), BikeType.OTHER, BikeBrand.OTHER, "", "", 29, "")

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