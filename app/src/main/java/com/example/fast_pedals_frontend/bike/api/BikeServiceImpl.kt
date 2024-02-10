package com.example.fast_pedals_frontend.bike.api

import android.util.Log
import com.example.fast_pedals_frontend.AuthSharedPreferences
import com.example.fast_pedals_frontend.bike.ContactInfo
import com.example.fast_pedals_frontend.listing.api.ListingResponse
import org.json.JSONObject
import retrofit2.Response
import java.util.Base64

class BikeServiceImpl(
    private val bikeApi: BikeApi

): BikeService{
    override suspend fun getBike(bikeId: Long): BikeResponse {
        return bikeApi.getBike(bikeId)
    }

    override suspend fun getListing(listingId: Long): ListingResponse {
        return bikeApi.getListing(listingId)
    }

    override suspend fun getContactInfo(userId: Long): ContactInfo {
        return bikeApi.getContactInfo(userId)
    }

    override suspend fun favouriteCheck(listingId: Long): Response<Boolean> {

        val tokenParts = AuthSharedPreferences.getJwtToken().split(".")

        val payloadJson = JSONObject(decodeBase64(tokenParts[1]))

        val userEmail = payloadJson.getString("sub")

        val favouriteRequest = FavouriteRequest(userEmail, listingId)

        return bikeApi.favouriteCheck(favouriteRequest)

    }

    override suspend fun favourite(listingId: Long): Response<Unit> {

        val tokenParts = AuthSharedPreferences.getJwtToken().split(".")

        val payloadJson = JSONObject(decodeBase64(tokenParts[1]))

        val userEmail = payloadJson.getString("sub")

        val favouriteRequest = FavouriteRequest(userEmail, listingId)

        return bikeApi.favourite(favouriteRequest)

    }

    override suspend fun unFavourite(listingId: Long): Response<Unit> {

        val tokenParts = AuthSharedPreferences.getJwtToken().split(".")

        val payloadJson = JSONObject(decodeBase64(tokenParts[1]))

        val userEmail = payloadJson.getString("sub")

        val favouriteRequest = FavouriteRequest(userEmail, listingId)

        return bikeApi.unFavourite(favouriteRequest)

    }

    fun decodeBase64(input: String): String {
        val decodedBytes = Base64.getDecoder().decode(input)
        return String(decodedBytes)
    }

}