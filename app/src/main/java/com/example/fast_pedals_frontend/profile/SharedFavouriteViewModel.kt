package com.example.fast_pedals_frontend.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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