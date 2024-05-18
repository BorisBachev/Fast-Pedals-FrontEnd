package com.example.fast_pedals_frontend.listing.api

import com.example.fast_pedals_frontend.create.api.request_response.PresignedResponse
import com.example.fast_pedals_frontend.listing.api.ListingDestinations.FAVOURITES
import com.example.fast_pedals_frontend.listing.api.ListingDestinations.PRESIGNED_DOWNLOAD
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ListingApi {

    @GET(FAVOURITES)
    suspend fun getFavourites(): Response<List<ListingResponse>>

    @POST(PRESIGNED_DOWNLOAD)
    suspend fun getImageLink(
        @Query("key") key: String
    ): Response<PresignedResponse>

    @GET
    suspend fun downloadImage(
        @Url imageUrl: String
    ): Response<ResponseBody>

}
