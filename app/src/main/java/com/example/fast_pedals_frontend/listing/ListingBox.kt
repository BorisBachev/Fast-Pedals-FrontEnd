package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.fast_pedals_frontend.R

import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme
import java.io.File


@Composable
fun ListingBox(
    listing: ListingResponse,
    hasImage: Boolean,
    imageFile: File?,
    onClick: () -> Unit = {}
) {
    val imagePainter = rememberAsyncImagePainter(imageFile)

    FastPedalsFrontEndTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(4.dp))
                ) {
                        if (hasImage) {
                            Image(
                                painter = imagePainter,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            val placeholderPainter = painterResource(id = R.drawable.placeholder_bike)
                            Image(
                                painter = placeholderPainter,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = listing.title, fontWeight = FontWeight.Bold)
                    Text(text = "Description: ${listing.description}")
                    Text(text = "Price: ${listing.price}")
                    Text(text = "Location: ${listing.location}")
                }
            }
        }
    }

