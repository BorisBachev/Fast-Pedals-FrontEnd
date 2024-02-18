package com.example.fast_pedals_frontend.edit.api

import com.example.fast_pedals_frontend.edit.api.request_response.EditRequest
import com.example.fast_pedals_frontend.edit.api.request_response.EditResponse
import retrofit2.Response

interface EditService {

    suspend fun editListing(
        editRequest: EditRequest
    ): Response<EditResponse>

}