package com.capstone.enviro.data.remote

import okhttp3.Interceptor

/**
 * AuthInterceptor is used to intercept network requests and add authentication headers.
 * It can be used to add tokens or other authentication information to the request headers.
 */
class AuthInterceptor(
    private val tokenManager: TokenManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()

        return if (token != null) {
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}