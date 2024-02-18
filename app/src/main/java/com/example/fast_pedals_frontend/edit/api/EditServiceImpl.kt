package com.example.fast_pedals_frontend.edit.api

import com.example.fast_pedals_frontend.edit.api.request_response.EditRequest
import com.example.fast_pedals_frontend.edit.api.request_response.EditResponse
import retrofit2.Response

class EditServiceImpl(

    private val editApi: EditApi

): EditService {

    override suspend fun editListing(editRequest: EditRequest): Response<EditResponse> {
        return editApi.editListing(editRequest)
    }

}