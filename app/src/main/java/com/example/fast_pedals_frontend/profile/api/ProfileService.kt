package com.example.fast_pedals_frontend.profile.api

import com.example.fast_pedals_frontend.listing.api.FavouriteRequest
import retrofit2.Response

interface ProfileService {

    suspend fun getUser(id: Long): Response<UserResponse>

    suspend fun getUserByEmail(): Response<UserResponse>

    suspend fun logout(): Response<Unit>

}