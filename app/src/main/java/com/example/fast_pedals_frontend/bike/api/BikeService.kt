package com.example.fast_pedals_frontend.bike.api

import com.example.fast_pedals_frontend.bike.ContactInfo
import com.example.fast_pedals_frontend.listing.ListingResponse

interface BikeService {

    suspend fun getBike(bikeId: Long): BikeResponse
    suspend fun getListing(listingId: Long): ListingResponse

    suspend fun getContactInfo(userId: Long): ContactInfo

}