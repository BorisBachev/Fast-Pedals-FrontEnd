package com.example.fast_pedals_frontend.bike

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.fast_pedals_frontend.R
import com.example.fast_pedals_frontend.bike.api.response.WholeListingResponse
import com.example.fast_pedals_frontend.edit.SharedEditViewModel
import com.example.fast_pedals_frontend.listing.ListingViewModel
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeScreen(

    bikeViewModel: BikeViewModel,
    listingId: Long,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: () -> Unit,
    onListingsClick: () -> Unit,
    sharedEditViewModel: SharedEditViewModel,
    sharedCriteriaViewModel: SharedCriteriaViewModel,
    listingViewModel: ListingViewModel

) {

    val state by bikeViewModel.bikeState
    val wholeListing by bikeViewModel.wholeListing.collectAsState()

    val isFavourite = remember { mutableStateOf(false) }
    val isOwner = remember { mutableStateOf(false) }
    val shouldShowImages = wholeListing?.images?.isNotEmpty() ?: false &&
            wholeListing?.images?.all { it.isNotBlank() } ?: false

    LaunchedEffect(Unit) {
        bikeViewModel.getWholeListing(listingId)
    }

    LaunchedEffect(wholeListing) {
        isFavourite.value = wholeListing?.isFavourite ?: false
        isOwner.value = wholeListing?.isOwner ?: false
    }

    val iconTint = if (isFavourite.value) Color.Red else Color.LightGray

    if (state is BikeState.Error) {
        Text(
            text = (state as BikeState.Error).errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(8.dp)
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }

    if (state is BikeState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    val context = LocalContext.current

    wholeListing?.let { listingViewModel.fetchMultipleImages(context, it.images) }

    val imageFiles by listingViewModel.imageFiles.collectAsState()

    val images = imageFiles.filterKeys { it in (wholeListing?.images ?: emptyList()) }

    FastPedalsFrontEndTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = wholeListing?.title ?: "",
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (isFavourite.value) {
                                    bikeViewModel.unFavourite(listingId)
                                } else {
                                    bikeViewModel.favourite(listingId)
                                }
                                isFavourite.value = !isFavourite.value
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavourite.value) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = iconTint
                            )
                        }
                    }
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    if (shouldShowImages) {
                        wholeListing?.let { SwipeableImageGallery(it.images, images) }
                    } else {
                        PlaceholderImage()
                    }

                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                    ) {
                        item {

                            val bikeInfo = "${wholeListing?.brand ?: ""} ${wholeListing?.model ?: ""}"

                            Text(
                                text = bikeInfo,
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(8.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Icon(Icons.Default.Money, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${wholeListing?.price} USD",
                                    style = TextStyle(fontSize = 16.sp),
                                )
                            }

                            wholeListing?.let { DetailsBox(bike = it) }

                            DescriptionBox(description = wholeListing?.description ?: "")

                            LocationBox(location = wholeListing?.location ?: "")

                            wholeListing?.let { ContactInfoBox(contactInfo = wholeListing!!) }

                            Button(
                                onClick = {
                                    sharedCriteriaViewModel.resetSearchCriteria()
                                    sharedCriteriaViewModel.updateUserId(wholeListing?.userId)
                                    onListingsClick()
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Icon(Icons.Default.LocationOn, contentDescription = null)
                                Text("All from User")
                            }

                        }
                    }
                }
            },
            bottomBar = {
                if (isOwner.value) {
                    wholeListing?.let {
                        sharedEditViewModel.setEditRequest(it)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                bikeViewModel.deleteListing {
                                    onDelete()
                                }
                                      },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Delete Listing")
                        }

                        Button(
                            onClick = {
                                bikeViewModel.resetState()
                                onEdit(listingId)
                                      },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Edit Listing")
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableImageGallery(imageKeys: List<String>, imageFiles: Map<String, File>) {
    val pagerState = rememberPagerState(pageCount = { imageKeys.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        val imageKey = imageKeys.getOrNull(page)
        val imageFile = imageKey?.let { imageFiles[it] }
        if (imageFile != null) {
            Image(
                painter = rememberAsyncImagePainter(imageFile),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Composable
fun PlaceholderImage() {
    val placeholderPainter = painterResource(id = R.drawable.placeholder_bike)
    Image(
        painter = placeholderPainter,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth().height(200.dp)
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DetailsBox(bike: WholeListingResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.DirectionsBike, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Bike Details",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            BikeDetailItem("Type", bike.type.toString())
            BikeDetailItem("Size", bike.size)
            BikeDetailItem("Wheel Size", bike.wheelSize.toString())
            BikeDetailItem("Frame Material", bike.frameMaterial)
        }
    }
}

@Composable
fun BikeDetailItem(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = title, modifier = Modifier.weight(1f))
        Text(text = value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DescriptionBox(description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Outlined.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Description",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Text(text = description)
        }
    }
}

@Composable
fun LocationBox(location: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(Icons.Outlined.Place, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Location",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = location)
            }
        }
    }
}

@Composable
fun ContactInfoBox(contactInfo: WholeListingResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.ContactMail, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Contact Information",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = contactInfo.name)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = contactInfo.phoneNumber)
            }
        }
    }
}