package com.example.fast_pedals_frontend.edit.api

import com.example.fast_pedals_frontend.edit.api.EditDestinations.EDIT
import com.example.fast_pedals_frontend.edit.api.request_response.EditRequest
import com.example.fast_pedals_frontend.edit.api.request_response.EditResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface EditApi {

    @PUT(EDIT)
    suspend fun editListing(
        @Body editRequest: EditRequest
    ): Response<EditResponse>

}