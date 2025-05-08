package com.capstone.enviro.data.remote

import android.content.Context
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class TokenManager(
    private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("enviro_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit { putString("auth_token", token) }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    suspend fun getValidToken(): String? {
        return FirebaseAuth.getInstance().currentUser?.getIdToken(true)
            ?.await()
            ?.token
    }

    fun clearToken() {
        sharedPreferences.edit { remove("auth_token") }
    }
}