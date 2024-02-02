package com.example.fast_pedals_frontend

import android.content.Context
import android.content.SharedPreferences

object AuthSharedPreferences {
    private const val PREFS_NAME = "jwt"
    private const val KEY_JWT_TOKEN = "jwt_token"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveJwtToken(token: String) {
        sharedPreferences.edit().putString(KEY_JWT_TOKEN , token).apply()
    }

    fun getJwtToken(): String {
        return sharedPreferences.getString(KEY_JWT_TOKEN , "") ?: ""
    }
}