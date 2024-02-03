package com.example.fast_pedals_frontend

import com.example.fast_pedals_frontend.RetrofitHost.EMULATOR
import com.example.fast_pedals_frontend.RetrofitHost.PHONE
import com.example.fast_pedals_frontend.search.SearchViewModel
import com.example.fast_pedals_frontend.auth.AuthApi
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.auth.AuthServiceImpl
import com.example.fast_pedals_frontend.auth.login.LogInViewModel
import com.example.fast_pedals_frontend.auth.register.RegisterViewModel
import com.example.fast_pedals_frontend.listing.ListingApi
import com.example.fast_pedals_frontend.listing.ListingService
import com.example.fast_pedals_frontend.listing.ListingServiceImpl
import com.example.fast_pedals_frontend.listing.ListingViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        AuthSharedPreferences.apply {
            init(androidContext())
        }
    }

    single(named("Retrofit")) {

        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(get()))
            .addInterceptor(httpInterceptor)

        Retrofit.Builder()
            .baseUrl(EMULATOR)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }

    single<AuthApi>(named("AuthApi")) {
        get<Retrofit>(named("Retrofit")).create(AuthApi::class.java)
    }

    single(named("ListingApi")) {
        get<Retrofit>(named("Retrofit")).create(ListingApi::class.java)
    }
    single<AuthService> {
        AuthServiceImpl(get(named("AuthApi")), get())
    }
    single<ListingService> {
        ListingServiceImpl(get(named("ListingApi")))
    }
    viewModel {
        LogInViewModel(get<AuthService>())
    }
    viewModel{
        RegisterViewModel(get<AuthService>())
    }
    viewModel {
        SearchViewModel()
    }
    viewModel {
        ListingViewModel(get())
    }

}