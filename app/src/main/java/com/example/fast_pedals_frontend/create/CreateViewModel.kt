package com.example.fast_pedals_frontend.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import com.example.fast_pedals_frontend.create.api.CreateService
import com.example.fast_pedals_frontend.create.api.request_response.CreateRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.io.File
import java.util.UUID

class CreateViewModel(

    private val createService: CreateService

): ViewModel() {

    private val _createState = MutableStateFlow<CreateState>(CreateState.None)
    val createState: StateFlow<CreateState> = _createState

    private val _isTypeDropdownExpanded = MutableStateFlow(false)
    val isTypeDropdownExpanded: StateFlow<Boolean> = _isTypeDropdownExpanded

    private val _isBrandDropdownExpanded = MutableStateFlow(false)
    val isBrandDropdownExpanded: StateFlow<Boolean> = _isBrandDropdownExpanded

    private val _isEditingImages = MutableStateFlow(false)
    val isEditingImages: StateFlow<Boolean> = _isEditingImages

    private val _areImagesPicked = MutableStateFlow(false)
    val areImagesPicked: StateFlow<Boolean> = _areImagesPicked

    private val _createRequest = MutableStateFlow(
        CreateRequest("", "", 0.0, "", date = LocalDate.now().toString() , listOf(), BikeType.OTHER, BikeBrand.OTHER, "", "", 29, "")
    )
    val createRequest: StateFlow<CreateRequest> get() = _createRequest

    private val _imageUris = MutableStateFlow<List<Uri>>(listOf())
    val imageUris: StateFlow<List<Uri>> = _imageUris

    private val _fileNames = MutableStateFlow<List<String>>(listOf())

    fun create(files: List<File>, onCreate: (Long) -> Unit) {
        viewModelScope.launch {
            _createState.value = CreateState.Loading

            try {
                val fileNames = mutableListOf<String>()

                files.forEach { file ->
                    val key = "${file.name}-${generateRandomString()}.${file.extension}"
                    fileNames.add(key)
                }

                val success = uploadImages(files, fileNames)

                if (success) {
                    val createRequest = _createRequest.value.copy(images = fileNames)
                    val response = createService.createListing(createRequest)

                    if (response.isSuccessful) {
                        _createState.value = CreateState.Success
                        response.body()?.id?.let { onCreate(it) }
                    } else {
                        _createState.value = CreateState.Error(response.message())
                    }
                } else {
                    _createState.value = CreateState.Error("Failed to upload one or more images")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _createState.value = CreateState.Error("Failed to create listing: ${e.message}")
            }
        }
    }

    private suspend fun uploadImages(files: List<File>, fileNames: List<String>): Boolean = coroutineScope {
        val uploadTasks = files.mapIndexed { index, file ->
            async {
                val contentType = getContentType(file)
                val presignedUrl = getPresignedUrl(fileNames[index], contentType)

                if (presignedUrl != null) {
                    val imageBytes = file.readBytes()
                    uploadImage(presignedUrl, imageBytes, contentType)
                } else {
                    false
                }
            }
        }

        val results = uploadTasks.awaitAll()
        results.all { it }
    }

    private fun generateRandomString(): String {
        return UUID.randomUUID().toString()
    }

    private fun getContentType(file: File): String {
        return when (file.extension) {
            "jpg" -> "image/jpeg"
            "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            else -> "image/jpeg"
        }
    }

    private suspend fun getPresignedUrl(key: String, contentType: String): String? {
        val response = createService.generatePresignedUploadUrl(key, contentType)
        return if (response.isSuccessful) {
            response.body()?.presignedUrl
        } else {
            null
        }
    }

    private suspend fun uploadImage(presignedUrl: String, image: ByteArray, contentType: String): Boolean {
        val response = createService.uploadImage(presignedUrl, image, contentType)
        return response.isSuccessful
    }

    fun toggleEditingImages() {
        _isEditingImages.value = !_isEditingImages.value
    }

    fun toggleImagesPicked() {
        _areImagesPicked.value = !_areImagesPicked.value
    }

    fun updateImageUris(newImageUris: List<Uri>) {
        _imageUris.value = newImageUris
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
        _createRequest.value = CreateRequest("", "", 0.0, "", date = LocalDate.now().toString() , listOf(), BikeType.OTHER, BikeBrand.OTHER, "", "", 29, "")

    }

    fun toggleTypeDropdown() {
        _isTypeDropdownExpanded.value = !_isTypeDropdownExpanded.value
    }

    fun toggleBrandDropdown() {
        _isBrandDropdownExpanded.value = !_isBrandDropdownExpanded.value
    }


}