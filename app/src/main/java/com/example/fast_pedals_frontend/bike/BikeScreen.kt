package com.example.fast_pedals_frontend.bike

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fast_pedals_frontend.R
import com.example.fast_pedals_frontend.bike.api.BikeResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeScreen(
    bikeViewModel: BikeViewModel,
    listingId: Long,
    onBack: () -> Unit
) {

    val state by bikeViewModel.bikeState
    val listing by bikeViewModel.listing.collectAsState()
    val bike by bikeViewModel.bike.collectAsState()

    bikeViewModel.getListing(listingId)
    listing?.let { bikeViewModel.getBike(it.bikeId) }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(
                    text = listing?.title ?: "",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                ) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
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
                val imageResources = listOf(
                    R.drawable.cruz,
                    R.drawable.cruz,
                    R.drawable.cruz
                )

                SwipeableImageGallery(imageResources = imageResources)

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    item {

                        val bikeInfo = "${bike?.brand ?: ""} ${bike?.model ?: ""}"

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
                                text = "${listing?.price} USD",
                                style = TextStyle(fontSize = 16.sp),
                            )
                        }

                        bike?.let { DetailsBox(bike = it) }

                        DescriptionBox(description = listing?.description ?: "")

                        LocationBox(location = listing?.location ?: "")
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableImageGallery(imageResources: List<Int>) {
    val pagerState = rememberPagerState(pageCount = { imageResources.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        Image(
            painter = painterResource(id = imageResources[page]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

@Composable
fun DetailsBox(bike: BikeResponse) {
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
                Icon(Icons.Default.DirectionsBike, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Bike Details",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            BikeDetailItem("Type", (bike?.type ?: "").toString())
            BikeDetailItem("Size", bike?.size ?: "")
            BikeDetailItem("Wheel Size", (bike?.wheelSize ?: "").toString())
            BikeDetailItem("Frame Material", bike?.frameMaterial ?: "")
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