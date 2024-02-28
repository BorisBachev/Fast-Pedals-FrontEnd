package com.example.fast_pedals_frontend.bike.api

object BikeDestinations {
    const val BIKE = "/api/bike/{bikeId}"
    const val LISTING = "/api/listing/user/whole/{listingId}"
    const val FAVOURITE = "/api/favourite/{listingId}"
    const val FAVOURITE_CHECK = "/api/favourite/check/{listingId}"
    const val FAVOURITE_DELETE = "/api/favourite/delete/{listingId}"
}