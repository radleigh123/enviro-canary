package com.capstone.enviro.domain.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import com.capstone.enviro.domain.model.User
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.PUT

interface UserService {

    @POST("/user")
    fun createUser(@Body user: User): Call<User>

    @GET("/user/{id}")
    fun getUserById(@Path("id") uid: String): Call<User>

    @PUT("/user/{id}")
    fun updateUserById(@Path("id") uid: String, @Body user: User): Call<User>

    @GET("/users")
    fun getUsers(): Call<List<User>>

    @GET("/user/email/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User>

}