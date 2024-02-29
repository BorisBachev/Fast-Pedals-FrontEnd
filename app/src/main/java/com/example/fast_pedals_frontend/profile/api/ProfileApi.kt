package com.example.fast_pedals_frontend.profile.api

import com.example.fast_pedals_frontend.profile.api.ProfileDestinations.USER
import com.example.fast_pedals_frontend.profile.api.ProfileDestinations.USER_EMAIL
import com.example.fast_pedals_frontend.profile.api.ProfileDestinations.USER_LOGOUT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET(USER)
    suspend fun getUser(
        @Path("id") id: Long
    ): Response<UserResponse>

    @GET(USER_EMAIL)
    suspend fun getUserByEmail(): Response<UserResponse>

    @GET(USER_LOGOUT)
    suspend fun logout(): Response<Unit>

}