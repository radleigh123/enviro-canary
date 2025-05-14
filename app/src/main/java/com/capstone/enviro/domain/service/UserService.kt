package com.capstone.enviro.domain.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import com.capstone.enviro.domain.model.User
import retrofit2.http.Body
import retrofit2.http.Path


interface UserService {

    @GET("/users")
    fun getUsers(): Call<List<User>>

    @GET("/user/uid/{userId}")
    fun getUserById(@Path("userId") userId: String): Call<User>

    @POST("/user")
    fun createUser(@Body user: User): Call<User>

}