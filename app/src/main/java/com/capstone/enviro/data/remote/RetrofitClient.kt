package com.capstone.enviro.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.11:8080/"
    private const val EMULATOR_IP = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun getRetrofitInstance(tokenManager: TokenManager, isEmulator: Boolean = false): Retrofit {
        val url = if (isEmulator) EMULATOR_IP else BASE_URL
        return Retrofit.Builder()
            .baseUrl(url)
            .client(createOkHttpClient(tokenManager))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun <reified T> createService(tokenManager: TokenManager, isEmulator: Boolean = false): T {
        return getRetrofitInstance(tokenManager, isEmulator).create(T::class.java)
    }

}