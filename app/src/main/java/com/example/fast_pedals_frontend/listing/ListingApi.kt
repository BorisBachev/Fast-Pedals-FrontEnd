package com.example.fast_pedals_frontend.listing

import com.example.fast_pedals_frontend.auth.AuthDestinations
import com.example.fast_pedals_frontend.auth.login.LoginRequest
import com.example.fast_pedals_frontend.auth.login.LoginResponse
import com.example.fast_pedals_frontend.auth.register.RegisterRequest
import com.example.fast_pedals_frontend.auth.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ListingApi {

    @GET(AuthDestinations.REGISTER)
    suspend fun getListings(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>
    @POST(AuthDestinations.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ) : Response<LoginResponse>

}
