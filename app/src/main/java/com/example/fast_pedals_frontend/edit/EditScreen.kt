package com.example.fast_pedals_frontend.edit

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(

    editViewModel: EditViewModel,
    listingId: Long,
    onEdit: (Long) -> Unit,
    sharedEditViewModel: SharedEditViewModel

    ) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isBrandDropdownExpanded by editViewModel.isBrandDropdownExpanded.collectAsState()
    val isTypeDropdownExpanded by editViewModel.isTypeDropdownExpanded.collectAsState()

    val editRequest by editViewModel.editRequest.collectAsState()

    Log.d("EditScreen", "Edit request: $editRequest")

    FastPedalsFrontEndTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Edit your listing") }
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
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        expanded = isBrandDropdownExpanded,
                                        onDismissRequest = { editViewModel.toggleBrandDropdown() }
                                    ) {
                                        BikeBrand.entries.forEach { brand ->
                                            DropdownMenuItem(
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
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        expanded = isTypeDropdownExpanded,
                                        onDismissRequest = { editViewModel.toggleTypeDropdown() }
                                    ) {
                                        BikeType.entries.forEach { type ->
                                            DropdownMenuItem(
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
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    editViewModel.edit()
                                    onEdit(listingId)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text("Edit")
                            }
                        }
                    }
                }
            }
        )
    }
}