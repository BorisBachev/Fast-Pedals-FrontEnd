package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.fast_pedals_frontend.R
import com.example.fast_pedals_frontend.profile.SharedFavouriteViewModel
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme
import java.lang.Thread.sleep

@Composable
fun ListingScreen(
    listingViewModel: ListingViewModel,
    sharedCriteriaViewModel: SharedCriteriaViewModel,
    sharedFavouriteViewModel: SharedFavouriteViewModel,
    onClick: (Long) -> Unit
) {

    val listingState by listingViewModel.listingState
    val listings by listingViewModel.listings.collectAsState()
    val searchCriteria by sharedCriteriaViewModel.searchCriteria.collectAsState()
    val isFavourite = sharedFavouriteViewModel.isFavourite.collectAsState()
    val isSearch = sharedCriteriaViewModel.isSearch.collectAsState()

    LaunchedEffect(Unit){
        if(isFavourite.value) {
            sharedCriteriaViewModel.resetSearchCriteria()
            listingViewModel.getFavourites()
        } else if (isSearch.value) {
            listingViewModel.search(searchCriteria)
        } else {
            listingViewModel.getAllListings()
        }
    }

    val imagePainter = painterResource(id = R.drawable.cruz)

    var listingBoxClicked by remember { mutableStateOf(false) }

    FastPedalsFrontEndTheme {
        if (listings?.isEmpty() == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sorry, couldn't find any listings."
                )
            }
        } else {
            LazyColumn {
                items(listings.orEmpty()) { listing ->
                    ListingBox(listing = listing, imagePainter = imagePainter, onClick = {
                        listingBoxClicked = true
                        onClick(listing.id)
                    })
                }
            }
        }
    }

    DisposableEffect(listingBoxClicked) {
        onDispose {
            if (!listingBoxClicked) {
                sharedFavouriteViewModel.setIsFavourite(false)
                sharedCriteriaViewModel.updateIsSearch(false)
            }
        }
    }
}
