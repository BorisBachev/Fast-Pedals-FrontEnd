package com.example.fast_pedals_frontend

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val authSharedPreferences: AuthSharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest: Request = chain.request()

        val isLoginOrRegistration = isLoginOrRegistrationRequest(originalRequest.url.toString())

        val token: String? = if (!isLoginOrRegistration) {
            authSharedPreferences.getJwtToken()
        } else {
            null
        }

        val newRequest: Request = originalRequest.newBuilder()
            .apply {
                token?.let { header("Authorization", "Bearer $it") }
            }
            .build()

        return chain.proceed(newRequest)
    }

    private fun isLoginOrRegistrationRequest(requestUrl: String): Boolean {
        return requestUrl.contains("auth")
    }

}