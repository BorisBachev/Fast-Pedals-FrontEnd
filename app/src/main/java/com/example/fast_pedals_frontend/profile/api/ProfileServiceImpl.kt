package com.example.fast_pedals_frontend.profile.api

import android.util.Log
import com.example.fast_pedals_frontend.listing.api.FavouriteRequest
import retrofit2.Response

class ProfileServiceImpl(

    private val profileApi: ProfileApi

): ProfileService{

    override suspend fun getUser(id: Long): Response<UserResponse> {
        return profileApi.getUser(id)
    }

    override suspend fun getUserByEmail(): Response<UserResponse> {
        return profileApi.getUserByEmail()
    }

    override suspend fun logout(): Response<Unit> {
        return profileApi.logout()
    }

}