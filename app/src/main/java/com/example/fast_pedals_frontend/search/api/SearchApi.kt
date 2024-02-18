package com.example.fast_pedals_frontend.search.api

import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.listing.api.ListingDestinations
import com.example.fast_pedals_frontend.search.api.SearchDestinations.SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET(SEARCH)
    suspend fun search(
        @Query ("title") title: String?,
        @Query ("minPrice") minPrice: Double?,
        @Query ("maxPrice") maxPrice: Double?,
        @Query ("location") location: String?,
        @Query ("description") description: String?,
        @Query ("type") type: String?,
        @Query ("brand") brand: String?,
        @Query ("model") model: String?,
        @Query ("size") size: String?,
        @Query("wheelSize") wheelSize: Int?,
        @Query ("frameMaterial") frameMaterial: String?
    ): Response<List<ListingResponse>>

}
