package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import com.example.fast_pedals_frontend.R

@Composable
fun ListingScreen(
    listingViewModel: ListingViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: (String) -> Unit
) {

    val listingState by listingViewModel.listingState

    val previews: List<ListingResponse> = listOf(
        ListingResponse(
            id = 1,
            title = "Mountain Bike",
            description = "A high-performance mountain bike for adventurous rides.",
            price = 799.99,
            location = "Mountain View",
            image = "url_to_image1",
            bikeId = 12345,
            userId = 6789
        ),
        ListingResponse(
            id = 2,
            title = "City Cruiser",
            description = "A comfortable city cruiser for urban commuting.",
            price = 499.99,
            location = "City Center",
            image = "url_to_image2",
            bikeId = 54321,
            userId = 9876
        ),
        ListingResponse(
            id = 3,
            title = "Road Bike",
            description = "A lightweight road bike for speed enthusiasts.",
            price = 1299.99,
            location = "Suburbia",
            image = "url_to_image3",
            bikeId = 67890,
            userId = 5432
        ))


    /*listingViewModel.getPreviews { response ->

        previews = if (response != null) {
            response
        } else {
            emptyList()
        }
    }*/

    val imagePainter = painterResource(id = R.drawable.cruz)

    LazyColumn {
        items(previews) { listing ->
            ListingBox(listing = listing, imagePainter = imagePainter)
        }
    }

}