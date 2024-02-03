package com.example.fast_pedals_frontend.listing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun ListingScreen(
    listingViewModel: ListingViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: (String) -> Unit
) {

    val listingState by listingViewModel.listingState

    var previews: List<ListingResponse> = emptyList()

    listingViewModel.getPreviews { response ->
        previews = if (response != null) {
            response
        } else {
            emptyList()
        }
    }



}