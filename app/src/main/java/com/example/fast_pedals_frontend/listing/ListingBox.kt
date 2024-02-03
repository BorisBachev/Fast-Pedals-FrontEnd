package com.example.fast_pedals_frontend.listing

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.fast_pedals_frontend.R

@Composable
fun ListingBox() {

    val image = painterResource(id = R.drawable.cruz)

    Image(painter = image, contentDescription = "Pedal")

}

@Preview
@Composable
fun ListingBoxPreview() {
    ListingBox()
}