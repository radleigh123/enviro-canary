package com.capstone.enviro.domain.service

import retrofit2.http.POST
import com.capstone.enviro.domain.model.Notification
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT

interface NotificationService {

    @POST("/notification")
    fun createNotification(notification: Notification): Call<Notification>

    @GET("/notification/{id}")
    fun getNotificationById(id: String): Call<Notification>

    @PUT("/notification/{id}")
    fun updateNotificationById(id: String): Call<Notification>

}