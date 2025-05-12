package com.capstone.enviro

import android.content.Context
import android.util.Log
import androidx.core.content.edit

object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_UID = "uid"

    fun saveUid(context: Context, uid: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit { putString(KEY_UID, uid) }
    }

    fun getUid(context: Context): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_UID, null)
    }

    fun saveUserSession(context: Context, uid: String, email: String?, displayName: String?) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_UID, uid)
            putString("email", email)
            putString("name", displayName)
            apply()
        }
        Log.d("SessionManager", "User session saved: uid=$uid, email=$email, name=$displayName")
    }

    fun getUserSession(context: Context): Map<String, String?> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return mapOf(
            KEY_UID to prefs.getString(KEY_UID, null),
            "email" to prefs.getString("email", null),
            "name" to prefs.getString("name", null)
        )
    }

    fun clearUserSession(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            remove(KEY_UID)
            remove("email")
            remove("name")
            apply()
        }
    }

}