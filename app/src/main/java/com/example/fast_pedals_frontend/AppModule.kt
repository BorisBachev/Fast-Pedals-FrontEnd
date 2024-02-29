package com.example.fast_pedals_frontend

import com.example.fast_pedals_frontend.RetrofitHost.EMULATOR
import com.example.fast_pedals_frontend.RetrofitHost.PHONE
import com.example.fast_pedals_frontend.RetrofitHost.PHONE_HOTSPOT
import com.example.fast_pedals_frontend.RetrofitHost.PHONE_OFFICE
import com.example.fast_pedals_frontend.search.SearchViewModel
import com.example.fast_pedals_frontend.auth.AuthApi
import com.example.fast_pedals_frontend.auth.AuthService
import com.example.fast_pedals_frontend.auth.AuthServiceImpl
import com.example.fast_pedals_frontend.auth.login.LogInViewModel
import com.example.fast_pedals_frontend.auth.register.RegisterViewModel
import com.example.fast_pedals_frontend.auth.start.StartViewModel
import com.example.fast_pedals_frontend.bike.BikeViewModel
import com.example.fast_pedals_frontend.bike.api.BikeApi
import com.example.fast_pedals_frontend.bike.api.BikeService
import com.example.fast_pedals_frontend.bike.api.BikeServiceImpl
import com.example.fast_pedals_frontend.create.CreateViewModel
import com.example.fast_pedals_frontend.create.api.CreateApi
import com.example.fast_pedals_frontend.create.api.CreateService
import com.example.fast_pedals_frontend.create.api.CreateServiceImpl
import com.example.fast_pedals_frontend.edit.EditViewModel
import com.example.fast_pedals_frontend.edit.SharedEditViewModel
import com.example.fast_pedals_frontend.edit.api.EditApi
import com.example.fast_pedals_frontend.edit.api.EditService
import com.example.fast_pedals_frontend.edit.api.EditServiceImpl
import com.example.fast_pedals_frontend.firebase.FirebaseViewModel
import com.example.fast_pedals_frontend.firebase.api.FirebaseApi
import com.example.fast_pedals_frontend.firebase.api.FirebaseService
import com.example.fast_pedals_frontend.firebase.api.FirebaseServiceImpl
import com.example.fast_pedals_frontend.listing.api.ListingApi
import com.example.fast_pedals_frontend.listing.api.ListingService
import com.example.fast_pedals_frontend.listing.api.ListingServiceImpl
import com.example.fast_pedals_frontend.listing.ListingViewModel
import com.example.fast_pedals_frontend.profile.ProfileViewModel
import com.example.fast_pedals_frontend.profile.SharedFavouriteViewModel
import com.example.fast_pedals_frontend.profile.api.ProfileApi
import com.example.fast_pedals_frontend.profile.api.ProfileService
import com.example.fast_pedals_frontend.profile.api.ProfileServiceImpl
import com.example.fast_pedals_frontend.search.SharedCriteriaViewModel
import com.example.fast_pedals_frontend.search.api.SearchApi
import com.example.fast_pedals_frontend.search.api.SearchService
import com.example.fast_pedals_frontend.search.api.SearchServiceImpl
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
            .baseUrl(PHONE_OFFICE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }


    single<AuthApi>(named("AuthApi")) {
        get<Retrofit>(named("Retrofit")).create(AuthApi::class.java)
    }
    single<ListingApi>(named("ListingApi")) {
        get<Retrofit>(named("Retrofit")).create(ListingApi::class.java)
    }
    single<BikeApi>(named("BikeApi")) {
        get<Retrofit>(named("Retrofit")).create(BikeApi::class.java)
    }
    single<SearchApi>(named("SearchApi")) {
        get<Retrofit>(named("Retrofit")).create(SearchApi::class.java)
    }
    single<CreateApi>(named("CreateApi")) {
        get<Retrofit>(named("Retrofit")).create(CreateApi::class.java)
    }
    single<EditApi>(named("EditApi")) {
        get<Retrofit>(named("Retrofit")).create(EditApi::class.java)
    }
    single<ProfileApi>(named("ProfileApi")) {
        get<Retrofit>(named("Retrofit")).create(ProfileApi::class.java)
    }
    single<FirebaseApi>(named("FirebaseApi")) {
        get<Retrofit>(named("Retrofit")).create(FirebaseApi::class.java)
    }


    single<AuthService> {
        AuthServiceImpl(get(named("AuthApi")), get())
    }
    single<ListingService> {
        ListingServiceImpl(get(named("ListingApi")))
    }
    single<BikeService> {
        BikeServiceImpl(get(named("BikeApi")))
    }
    single<SearchService> {
        SearchServiceImpl(get(named("SearchApi")))
    }
    single<CreateService> {
        CreateServiceImpl(get(named("CreateApi")))
    }
    single<EditService> {
        EditServiceImpl(get(named("EditApi")))
    }
    single<ProfileService> {
        ProfileServiceImpl(get(named("ProfileApi")))
    }
    single<FirebaseService> {
        FirebaseServiceImpl(get(named("FirebaseApi")))
    }

    viewModel {
        LogInViewModel(get<AuthService>(), get<FirebaseViewModel>())
    }
    viewModel {
        RegisterViewModel(get<AuthService>(), get<FirebaseViewModel>())
    }
    viewModel {
        SearchViewModel()
    }
    viewModel {
        ListingViewModel(
            get<ListingService>(),
            get<SearchService>(),
            get<SharedCriteriaViewModel>()
        )
    }
    viewModel {
        BikeViewModel(get<BikeService>())
    }
    viewModel {
        SharedCriteriaViewModel()
    }
    viewModel {
        SharedEditViewModel()
    }
    viewModel {
        CreateViewModel(get<CreateService>())
    }
    viewModel {
        EditViewModel(get<EditService>())
    }
    viewModel {
        ProfileViewModel(get<ProfileService>(), get())
    }
    viewModel {
        SharedFavouriteViewModel()
    }
    viewModel {
        FirebaseViewModel(get<FirebaseService>())
    }
    viewModel {
        StartViewModel(get<AuthService>(), get<FirebaseViewModel>())
    }

}