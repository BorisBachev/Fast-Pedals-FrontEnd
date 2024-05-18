package com.example.fast_pedals_frontend.listing

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import java.io.File
import java.io.InputStream

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

    private val _imageFiles = MutableStateFlow(mapOf<String, File>())
    val imageFiles: StateFlow<Map<String, File>> = _imageFiles

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

    fun getAllListings() {

        val criteria = SearchCriteria("", null, null, "", "",
            null, null, "", "", wheelSize = null, "", userId = null)

        viewModelScope.launch {
            _listingState.value = ListingState.Loading
            val response = searchService.search(criteria)
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

    private fun generatePresignedDownloadUrl(key: String) : LiveData<String> {
        val urlLiveData = MutableLiveData<String>()

        viewModelScope.launch {
            val response = listingService.generatePresignedDownloadUrl(key)
            if (response.isSuccessful) {
                val url = response.body()?.presignedUrl.toString()
                urlLiveData.postValue(url)
            } else {
                _listingState.value = ListingState.Error("An error occurred while generating presigned download url")
            }
        }

        return urlLiveData

    }

    private suspend fun downloadImage(imageUrl: String, context: Context, fileName: String): File? {
        val response = listingService.downloadImage(imageUrl)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            responseBody?.let {
                saveImageToFile(it.byteStream(), fileName, context)
            }
        } else {
            null
        }
    }

    private fun saveImageToFile(inputStream: InputStream, fileName: String, context: Context): File {
        val tempDir = context.cacheDir
        val file = File(tempDir, fileName)

        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return file
    }

    private fun downloadAndSaveImage(key: String, context: Context) {

        val urlLiveData = generatePresignedDownloadUrl(key)

        urlLiveData.observeForever { url ->
            url?.let {
                viewModelScope.launch {
                    val response = downloadImage(url, context, key)
                    if (response != null) {
                        val currentImageFiles = _imageFiles.value.toMutableMap()
                        currentImageFiles[key] = response
                        _imageFiles.value = currentImageFiles
                    }
                }
            }
        }

    }

    fun fetchAllFirstImages(context: Context) {
        viewModelScope.launch {
            _listingState.value = ListingState.Loading
            _listings.value?.forEach { listing ->
                listing.images.firstOrNull()?.let { key ->
                    downloadAndSaveImage(key, context)
                }
            }
            _listingState.value = ListingState.Success
        }
    }

    fun fetchMultipleImages(context: Context, keys: List<String>) {
        viewModelScope.launch {
            keys.forEach { key ->
                downloadAndSaveImage(key, context)
            }
        }
    }

}