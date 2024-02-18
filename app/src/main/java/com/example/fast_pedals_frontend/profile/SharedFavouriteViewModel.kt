package com.example.fast_pedals_frontend.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fast_pedals_frontend.auth.login.LoginScreen
import com.example.fast_pedals_frontend.bike.Constants
import com.example.fast_pedals_frontend.listing.api.FavouriteRequest
import com.example.fast_pedals_frontend.profile.api.ProfileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedFavouriteViewModel(

): ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.None)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> get() = _isFavourite

    fun isFavourite(): Boolean {
        return _isFavourite.value
    }

    fun setIsFavourite(isFavourite: Boolean) {
        _isFavourite.value = isFavourite
    }

    fun getIsFavourite(): Boolean{
        return _isFavourite.value
    }

}