package com.capstone.enviro.api

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.GET

@Serializable
data class User(
    val email: String,
    val password: String
)

interface UserService {
    @GET("/users")
    fun getUsers(): Call<User>
}