package com.example.fast_pedals_frontend.search.api

import com.example.fast_pedals_frontend.bike.enums.BikeBrand
import com.example.fast_pedals_frontend.bike.enums.BikeType
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import com.example.fast_pedals_frontend.search.SearchCriteria
import retrofit2.Response

class SearchServiceImpl(
    private val searchApi: SearchApi
): SearchService {

    override suspend fun search(
        searchCriteria: SearchCriteria
    ): Response<List<ListingResponse>> {

        var brand: BikeBrand? = null
        var type: BikeType? = null

        if(!searchCriteria.brand?.name.equals("ALL")){
            brand = searchCriteria.brand
        }
        if(!searchCriteria.type?.name.equals("ALL")){
            type = searchCriteria.type
        }

        val response = searchApi.search(
            searchCriteria.title,
            searchCriteria.minPrice,
            searchCriteria.maxPrice,
            searchCriteria.location,
            searchCriteria.description,
            type?.toString(),
            brand?.toString(),
            searchCriteria.model,
            searchCriteria.size,
            searchCriteria.wheelSize,
            searchCriteria.frameMaterial
        )

        if (response.isSuccessful) {
            response.body()?.let {
                return Response.success(it)
            }
        }

        throw Exception("Error: ${response.code()}")

    }

}