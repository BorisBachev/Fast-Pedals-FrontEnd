package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import com.example.fast_pedals_frontend.R
import com.example.fast_pedals_frontend.listing.api.ListingState
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme

@Composable
fun ListingScreen(
    listingViewModel: ListingViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: (String) -> Unit,
    onClick: (Long) -> Unit
) {

    val listingState by listingViewModel.listingState

    val previews by listingViewModel.previews.collectAsState()

    listingViewModel.getPreviews()


    val imagePainter = painterResource(id = R.drawable.cruz)

    FastPedalsFrontEndTheme {
        LazyColumn {
            items(previews.orEmpty()) { listing ->
                ListingBox(listing = listing, imagePainter = imagePainter, onClick = {
                    onClick(listing.id)
                })
            }
        }
    }
}