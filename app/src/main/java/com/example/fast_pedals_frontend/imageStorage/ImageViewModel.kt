package com.example.fast_pedals_frontend.imageStorage

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.imageStorage.api.ImageService
import com.example.fast_pedals_frontend.imageStorage.api.ImageState
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.util.UUID

class ImageViewModel(
    private val imageService: ImageService,

    ): ViewModel() {

    private val _imageState = mutableStateOf<ImageState>(ImageState.None)
    val imageState: State<ImageState> = _imageState

    private val _imageFiles = MutableStateFlow(mapOf<String, File>())
    val imageFiles: StateFlow<Map<String, File>> = _imageFiles

    private val _imageUris = MutableStateFlow<List<Uri>>(listOf())
    val imageUris: StateFlow<List<Uri>> = _imageUris

    private fun generatePresignedDownloadUrl(key: String) : LiveData<String> {
        val urlLiveData = MutableLiveData<String>()

        viewModelScope.launch {
            val response = imageService.generatePresignedDownloadUrl(key)
            if (response.isSuccessful) {
                val url = response.body()?.presignedUrl.toString()
                urlLiveData.postValue(url)
            } else {
                _imageState.value = ImageState.Error("An error occurred while generating presigned download url")
            }
        }

        return urlLiveData

    }

    private suspend fun downloadImage(imageUrl: String, context: Context, fileName: String): File? {
        val response = imageService.downloadImage(imageUrl)
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

    fun fetchAllFirstImages(context: Context, listings: List<ListingResponse>) {
        viewModelScope.launch {
            _imageState.value = ImageState.Loading
            listings.forEach { listing ->
                listing.images.firstOrNull()?.let { key ->
                    downloadAndSaveImage(key, context)
                }
            }
            _imageState.value = ImageState.Success
        }
    }

    fun fetchMultipleImages(context: Context, keys: List<String>) {
        viewModelScope.launch {
            keys.forEach { key ->
                downloadAndSaveImage(key, context)
            }
        }
    }

    suspend fun uploadImages(files: List<File>, fileNames: List<String>): Boolean = coroutineScope {
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

    fun generateRandomString(): String {
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
        val response = imageService.generatePresignedUploadUrl(key, contentType)
        return if (response.isSuccessful) {
            response.body()?.presignedUrl
        } else {
            null
        }
    }

    private suspend fun uploadImage(presignedUrl: String, image: ByteArray, contentType: String): Boolean {
        val response = imageService.uploadImage(presignedUrl, image, contentType)
        return response.isSuccessful
    }

    fun updateImageUris(newImageUris: List<Uri>) {
        _imageUris.value = newImageUris
    }

}