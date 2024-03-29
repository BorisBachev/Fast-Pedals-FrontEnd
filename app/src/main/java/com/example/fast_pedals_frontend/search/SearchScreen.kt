package com.example.fast_pedals_frontend.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
fun SearchScreen(

    searchViewModel: SearchViewModel,
    sharedCriteriaViewModel: SharedCriteriaViewModel,
    onSearch: () -> Unit

) {

    val searchCriteria by sharedCriteriaViewModel.searchCriteria.collectAsState()

    sharedCriteriaViewModel.updateUserId(userId = null)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isBrandDropdownExpanded by searchViewModel.isBrandDropdownExpanded.collectAsState()
    val isTypeDropdownExpanded by searchViewModel.isTypeDropdownExpanded.collectAsState()

    FastPedalsFrontEndTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Find your perfect bike") }
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
                                            searchViewModel.toggleBrandDropdown()
                                        })
                                    ) {
                                        Text("Brand: ${searchCriteria.brand?.name ?: "ALL"}")
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Toggle Dropdown"
                                        )
                                    }
                                    DropdownMenu(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(8.dp),
                                        expanded = isBrandDropdownExpanded,
                                        onDismissRequest = { searchViewModel.toggleBrandDropdown() }
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .width(290.dp)
                                                .padding(end = 16.dp)
                                        ) {
                                            DropdownMenuItem(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = { Text("ALL", modifier = Modifier.padding(start = 16.dp)) },
                                                onClick = {
                                                    sharedCriteriaViewModel.updateBrand(null)
                                                    searchViewModel.toggleBrandDropdown()
                                                }
                                            )
                                            BikeBrand.entries.forEach { brand ->
                                                DropdownMenuItem(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = { Text(brand.name, modifier = Modifier.padding(start = 16.dp)) },
                                                    onClick = {
                                                        sharedCriteriaViewModel.updateBrand(brand)
                                                        searchViewModel.toggleBrandDropdown()
                                                    }
                                                )
                                            }
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
                                            searchViewModel.toggleTypeDropdown()
                                        })
                                    ) {
                                        Text("Type: ${searchCriteria.type?.name ?: "ALL"}")
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Toggle Dropdown"
                                        )
                                    }
                                    DropdownMenu(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(8.dp),
                                        expanded = isTypeDropdownExpanded,
                                        onDismissRequest = { searchViewModel.toggleTypeDropdown() }
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .width(290.dp)
                                                .padding(end = 8.dp)
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("ALL") },
                                                onClick = {
                                                    sharedCriteriaViewModel.updateType(null)
                                                    searchViewModel.toggleTypeDropdown()
                                                }
                                            )
                                            BikeType.entries.forEach { type ->
                                                DropdownMenuItem(
                                                    text = { Text(type.name) },
                                                    onClick = {
                                                        sharedCriteriaViewModel.updateType(type)
                                                        searchViewModel.toggleTypeDropdown()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = searchCriteria.title,
                                onValueChange = { sharedCriteriaViewModel.updateTitle(it) },
                                label = { Text("Listing Title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = searchCriteria.location,
                                onValueChange = { sharedCriteriaViewModel.updateLocation(it) },
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
                                value = searchCriteria.description,
                                onValueChange = { sharedCriteriaViewModel.updateDescription(it) },
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
                                value = searchCriteria.model,
                                onValueChange = { sharedCriteriaViewModel.updateModel(it) },
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
                                value = searchCriteria.size,
                                onValueChange = { sharedCriteriaViewModel.updateSize(it) },
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
                                value = if (searchCriteria.wheelSize != null) searchCriteria.wheelSize.toString() else "",
                                onValueChange = { newValue ->
                                    val newSize = newValue.toIntOrNull()
                                    if (!newValue.isNullOrEmpty()) {
                                        newSize?.let { sharedCriteriaViewModel.updateWheelSize(it) }
                                    } else {
                                        sharedCriteriaViewModel.updateWheelSize(null)
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
                                value = searchCriteria.frameMaterial,
                                onValueChange = { sharedCriteriaViewModel.updateFrameMaterial(it) },
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
                                value = searchCriteria.minPrice?.toString() ?: "",
                                onValueChange = { newValue ->
                                    if (newValue.isEmpty() || newValue == "0" || newValue == "0." || newValue == "0.0") {
                                        sharedCriteriaViewModel.updateMinPrice(null)
                                    } else {
                                        val newPrice = newValue.toDoubleOrNull()
                                        sharedCriteriaViewModel.updateMinPrice(newPrice)
                                    }
                                },
                                label = { Text("Min Price") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            OutlinedTextField(
                                value = searchCriteria.maxPrice?.toString() ?: "",
                                onValueChange = { newValue ->
                                    if (newValue.isEmpty() || newValue == "0" || newValue == "0." || newValue == "0.0") {
                                        sharedCriteriaViewModel.updateMaxPrice(null)
                                    } else {
                                        val newPrice = newValue.toDoubleOrNull()
                                        sharedCriteriaViewModel.updateMaxPrice(newPrice)
                                    }
                                },
                                label = { Text("Max Price") },
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
                                    sharedCriteriaViewModel.updateIsSearch(true)
                                    onSearch()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text("Search")
                            }
                        }
                    }
                }
            }
        )
    }
}

private fun Double.roundTo(decimals: Int): Double {
    return "%.${decimals}f".format(this).toDouble()
}