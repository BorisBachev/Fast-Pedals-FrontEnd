package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import com.example.fast_pedals_frontend.imageStorage.ImageViewModel
import com.example.fast_pedals_frontend.listing.api.ListingState
import com.example.fast_pedals_frontend.profile.SharedFavouriteViewModel
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme

@Composable
fun ListingScreen(
    listingViewModel: ListingViewModel,
    sharedCriteriaViewModel: SharedCriteriaViewModel,
    sharedFavouriteViewModel: SharedFavouriteViewModel,
    imageViewModel: ImageViewModel,
    onClick: (Long) -> Unit,
) {

    val listingState by listingViewModel.listingState
    val listings by listingViewModel.listings.collectAsState()
    val searchCriteria by sharedCriteriaViewModel.searchCriteria.collectAsState()
    val isFavourite = sharedFavouriteViewModel.isFavourite.collectAsState()
    val isSearch = sharedCriteriaViewModel.isSearch.collectAsState()

    val context = LocalContext.current

    //LaunchedEffect(Unit){
        if(isFavourite.value) {
            sharedCriteriaViewModel.resetSearchCriteria()
            listingViewModel.getFavourites()
        } else if (isSearch.value) {
            listingViewModel.search(searchCriteria)
        } else {
            listingViewModel.getAllListings()
        }
    //}

    LaunchedEffect(listings) {
        listings?.let {
            imageViewModel.fetchAllFirstImages(context, it)
        }
    }

    var listingBoxClicked by remember { mutableStateOf(false) }

    val imageFiles by imageViewModel.imageFiles.collectAsState()

    FastPedalsFrontEndTheme {
        /*if (listingState == ListingState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else*/
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
                itemsIndexed(listings.orEmpty()) { _, listing ->
                    val imageKey = listing.images.firstOrNull()
                    val imageFile = imageFiles[imageKey]
                    val hasImage = imageKey != null
                    ListingBox(
                        listing = listing,
                        imageFile = imageFile,
                        hasImage = hasImage,
                        onClick = {
                            listingBoxClicked = true
                            listingViewModel.resetListings()
                            onClick(listing.id)
                        }
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            sharedCriteriaViewModel.updateIsSearch(false)
            sharedFavouriteViewModel.setIsFavourite(false)
            listingViewModel.resetListings()
        }
    }

}
