package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.ui.theme.FastPedalsFrontEndTheme

@Composable
fun ListingBox(
    listing: ListingResponse,
    imagePainter: Painter,
    onClick: () -> Unit = {}
) {

    FastPedalsFrontEndTheme {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() },

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Cruz",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${listing.title}", fontWeight = FontWeight.Bold)
                Text(text = "Description: ${listing.description}")
                Text(text = "Price: ${listing.price}")
                Text(text = "Location: ${listing.location}")

            }
        }
    }
}