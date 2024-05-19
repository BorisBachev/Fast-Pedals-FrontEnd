package com.example.fast_pedals_frontend.edit

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.fast_pedals_frontend.create.CreateState
import com.example.fast_pedals_frontend.create.uriToFile
import com.example.fast_pedals_frontend.imageStorage.ImageViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(

    editViewModel: EditViewModel,
    listingId: Long,
    onBack: (Long) -> Unit,
    onEdit: () -> Unit,
    sharedEditViewModel: SharedEditViewModel,
    imageViewModel: ImageViewModel

    ) {

    val snackbarHostState = remember { SnackbarHostState() }

    val isBrandDropdownExpanded by editViewModel.isBrandDropdownExpanded.collectAsState()
    val isTypeDropdownExpanded by editViewModel.isTypeDropdownExpanded.collectAsState()
    val isEditingImages by editViewModel.isEditingImages.collectAsState()

    val editRequest by sharedEditViewModel.editRequest.collectAsState()

    val state by editViewModel.editState.collectAsState()

    if(state is EditState.Loading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (state is EditState.Error) {
        Text(
            text = (state as EditState.Error).message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(8.dp)
        )
    }

    val imageUris by imageViewModel.imageUris.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->

            if(isEditingImages) {
                imageViewModel.updateImageUris(imageUris + uris)
                editViewModel.toggleEditingImages()
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
                    title = { Text("Edit your listing") },
                    navigationIcon = {
                        IconButton(onClick = { onBack(listingId) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
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
                                            editViewModel.toggleBrandDropdown()
                                        })
                                    ) {
                                        Text("Brand: ${editRequest.brand.name}")
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
                                        onDismissRequest = { editViewModel.toggleBrandDropdown() }
                                    ) {
                                        BikeBrand.entries.forEach { brand ->
                                            DropdownMenuItem(
                                                modifier = Modifier.width(290.dp),
                                                text = { Text(brand.name) },
                                                onClick = {
                                                    sharedEditViewModel.updateBrand(brand)
                                                    editViewModel.toggleBrandDropdown()
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
                                            editViewModel.toggleTypeDropdown()
                                        })
                                    ) {
                                        Text("Type: ${editRequest.type.name}")
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
                                        onDismissRequest = { editViewModel.toggleTypeDropdown() }
                                    ) {
                                        BikeType.entries.forEach { type ->
                                            DropdownMenuItem(
                                                modifier = Modifier.width(290.dp),
                                                text = { Text(type.name) },
                                                onClick = {
                                                    sharedEditViewModel.updateType(type)
                                                    editViewModel.toggleTypeDropdown()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = editRequest.title,
                                onValueChange = { sharedEditViewModel.updateTitle(it) },
                                label = { Text("Listing Title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editRequest.location,
                                onValueChange = { sharedEditViewModel.updateLocation(it) },
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
                                value = editRequest.description,
                                onValueChange = { sharedEditViewModel.updateDescription(it) },
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
                                value = editRequest.model,
                                onValueChange = { sharedEditViewModel.updateModel(it) },
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
                                value = editRequest.size,
                                onValueChange = { sharedEditViewModel.updateSize(it) },
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
                                value = if (editRequest.wheelSize != null) editRequest.wheelSize.toString() else "",
                                onValueChange = { newValue ->
                                    val newSize = newValue.toIntOrNull()
                                    if (!newValue.isNullOrEmpty()) {
                                        newSize?.let { sharedEditViewModel.updateWheelSize(it) }
                                    } else {
                                        sharedEditViewModel.updateWheelSize(null)
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
                                value = editRequest.frameMaterial,
                                onValueChange = { sharedEditViewModel.updateFrameMaterial(it) },
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
                                value = editRequest.price?.toString() ?: "",
                                onValueChange = { newValue ->
                                    if (newValue.isEmpty() || newValue == "0" || newValue == "0." || newValue == "0.0") {
                                        sharedEditViewModel.updatePrice(null)
                                    } else {
                                        val newPrice = newValue.toDoubleOrNull()
                                        sharedEditViewModel.updatePrice(newPrice)
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
                                        editViewModel.toggleEditingImages()
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
                            Spacer(modifier = Modifier.height(16.dp))
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
                                    editViewModel.edit(editRequest, files, onEdit = {
                                        onEdit()
                                    })
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text("Edit")
                            }
                        }

                        if (state is EditState.Error) {
                            item {
                                Text(
                                    (state as EditState.Error).message,
                                    color = Color.Red,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        if (state is EditState.Loading) {
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