package com.example.fast_pedals_frontend.listing.api

import com.example.fast_pedals_frontend.listing.api.ListingDestinations.FAVOURITES
import retrofit2.Response
import retrofit2.http.GET

interface ListingApi {

    @GET(FAVOURITES)
    suspend fun getFavourites(): Response<List<ListingResponse>>

}
