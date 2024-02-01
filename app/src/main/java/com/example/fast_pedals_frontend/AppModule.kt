package com.example.fast_pedals_frontend

import com.example.fast_pedals_frontend.auth.AuthApi
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.auth.AuthServiceImpl
import com.example.fast_pedals_frontend.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    single<AuthService> {
        AuthServiceImpl(get())
    }
    viewModel {
        AuthViewModel(get())
    }
}