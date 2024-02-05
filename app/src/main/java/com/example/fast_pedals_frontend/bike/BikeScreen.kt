package com.example.fast_pedals_frontend.bike

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeScreen(

    bikeViewModel: BikeViewModel,
    onBack: () -> Unit

) {

    val state by bikeViewModel.bikeState

    val listing by bikeViewModel.listing.collectAsState()

    val bike by bikeViewModel.bike.collectAsState()

    LaunchedEffect(Unit) {
        listing?.let { bikeViewModel.getBike(1) }
    }

    bikeViewModel.getBike(1)

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Bike Listing") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )

        },
        content = {
            padding ->
            Column {
                if (state is BikeState.Loading) {
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp))
                } else {
                    if (state is BikeState.Error) {
                        Text(text = "Error loading bike data: ${(state as BikeState.Error).errorMessage}")
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(text = "Bike Details", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Title: ${listing?.title}")
                            Text(text = "Brand: ${bike?.brand}")
                            Text(text = "Model: ${bike?.model}")
                            Text(text = "Type: ${bike?.type}")
                            Text(text = "Price: ${listing?.price}")
                            Text(text = "Location: ${listing?.location}")
                            Text(text = "Description: ${listing?.description}")
                            Text(text = "Size: ${bike?.size}")
                            Text(text = "Wheel Size: ${bike?.wheelSize}")
                            Text(text = "Frame Material: ${bike?.frameMaterial}")
                        }
                    }
                }
            }
        }
    )



}