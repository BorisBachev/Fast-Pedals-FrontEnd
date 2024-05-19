package com.example.fast_pedals_frontend.create

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import com.example.fast_pedals_frontend.imageStorage.ImageViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    onCreate: (Long) -> Unit,
    createViewModel: CreateViewModel,
    imageViewModel: ImageViewModel
) {

    val createState by createViewModel.createState.collectAsState()

    val createRequest by createViewModel.createRequest.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val isBrandDropdownExpanded by createViewModel.isBrandDropdownExpanded.collectAsState()
    val isTypeDropdownExpanded by createViewModel.isTypeDropdownExpanded.collectAsState()
    val isEditingImages by createViewModel.isEditingImages.collectAsState()

    val imageUris by imageViewModel.imageUris.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            if(isEditingImages) {
                imageViewModel.updateImageUris(imageUris + uris)
                createViewModel.toggleEditingImages()
            } else {
                imageViewModel.updateImageUris(uris)
            }
        }
    )

    FastPedalsFrontEndTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Sell your old bike") }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        item {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)

                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable(onClick = {
                                            createViewModel.toggleBrandDropdown()
                                        })
                                    ) {
                                        Text("Brand: ${createRequest.brand.name}")
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Toggle Dropdown"
                                        )
                                    }
                                    DropdownMenu(
                                        modifier = Modifier
                                            .width(290.dp)
                                            .padding(8.dp),
                                        expanded = isBrandDropdownExpanded,
                                        onDismissRequest = { createViewModel.toggleBrandDropdown() }
                                    ) {
                                        BikeBrand.entries.forEach { brand ->
                                            DropdownMenuItem(
                                                modifier = Modifier.width(290.dp),
                                                text = { Text(brand.name) },
                                                onClick = {
                                                    createViewModel.updateBrand(brand)
                                                    createViewModel.toggleBrandDropdown()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)

                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable(onClick = {
                                            createViewModel.toggleTypeDropdown()
                                        })
                                    ) {
                                        Text("Type: ${createRequest.type.name}")
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Toggle Dropdown"
                                        )
                                    }
                                    DropdownMenu(
                                        modifier = Modifier
                                            .width(290.dp)
                                            .padding(8.dp),
                                        expanded = isTypeDropdownExpanded,
                                        onDismissRequest = { createViewModel.toggleTypeDropdown() }
                                    ) {
                                        BikeType.entries.forEach { type ->
                                            DropdownMenuItem(
                                                modifier = Modifier.width(290.dp),
                                                text = { Text(type.name) },
                                                onClick = {
                                                    createViewModel.updateType(type)
                                                    createViewModel.toggleTypeDropdown()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = createRequest.title,
                                onValueChange = { createViewModel.updateTitle(it) },
                                label = { Text("Listing Title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = createRequest.location,
                                onValueChange = { createViewModel.updateLocation(it) },
                                label = { Text("Location") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = createRequest.description,
                                onValueChange = { createViewModel.updateDescription(it) },
                                label = { Text("Description") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = createRequest.model,
                                onValueChange = { createViewModel.updateModel(it) },
                                label = { Text("Model") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = createRequest.size,
                                onValueChange = { createViewModel.updateSize(it) },
                                label = { Text("Size") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = if (createRequest.wheelSize != null) createRequest.wheelSize.toString() else "",
                                onValueChange = { newValue ->
                                    val newSize = newValue.toIntOrNull()
                                    if (!newValue.isNullOrEmpty()) {
                                        newSize?.let { createViewModel.updateWheelSize(it) }
                                    } else {
                                        createViewModel.updateWheelSize(null)
                                    }
                                },
                                label = { Text("Wheel Size") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = createRequest.frameMaterial,
                                onValueChange = { createViewModel.updateFrameMaterial(it) },
                                label = { Text("Frame Material") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = createRequest.price?.toString() ?: "",
                                onValueChange = { newValue ->
                                    if (newValue.isEmpty() || newValue == "0" || newValue == "0." || newValue == "0.0") {
                                        createViewModel.updatePrice(null)
                                    } else {
                                        val newPrice = newValue.toDoubleOrNull()
                                        createViewModel.updatePrice(newPrice)
                                    }
                                },
                                label = { Text("Price") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        createViewModel.toggleEditingImages()
                                        imagePickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                ) {
                                    Text("Add Images")
                                }
                                Button(
                                    onClick = {
                                        imagePickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                ) {
                                    Text("Change Images")
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            imageUris.chunked(3).forEach { rowUris ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    rowUris.forEach { uri ->
                                        Image(
                                            painter = rememberAsyncImagePainter(uri),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(120.dp)
                                                .padding(4.dp)
                                                .clip(shape = RoundedCornerShape(4.dp))
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                color = Color.Gray,
                                thickness = 1.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {

                                    val files = imageUris.mapNotNull { uri ->
                                        uriToFile(context, uri)
                                    }

                                    createViewModel.create(files) { id ->
                                        onCreate(id)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text("Create Listing")
                            }
                        }

                        if (createState is CreateState.Error) {
                            item {
                                Text(
                                    (createState as CreateState.Error).message,
                                    color = Color.Red,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        if (createState is CreateState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = getFileName(context, uri)
        val tempFile = File(context.cacheDir, fileName)
        tempFile.deleteOnExit()
        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        tempFile
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

private fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                result = if (displayNameIndex != -1) {
                    it.getString(displayNameIndex)
                } else {
                    "image"
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "temp_image"
}