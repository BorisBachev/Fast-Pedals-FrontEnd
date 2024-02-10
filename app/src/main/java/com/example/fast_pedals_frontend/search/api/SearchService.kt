package com.example.fast_pedals_frontend.search.api

import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.search.SearchCriteria
import retrofit2.Response

interface SearchService {

    suspend fun search(
        searchCriteria: SearchCriteria
    ): Response<List<ListingResponse>>

}