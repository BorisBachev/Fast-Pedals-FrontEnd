package com.example.fast_pedals_frontend.profile.api

data class UserResponse (

    val id: Long,
    var name: String,
    var email: String,
    var passw: String,
    var fullName: String,
    var phoneNumber: String,
    var profilePicture: String?

)