package com.capstone.enviro

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.capstone.enviro.domain.model.User

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

    fun saveUserSession(context: Context, user: User?) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            // Basic info
            putString(KEY_UID, user?.id.toString())
            putString("userId", user?.userId)
            putString("email", user?.email)
            putString("name", user?.name)

            // Physical info
            putString("age", user?.physicalAttributes?.age.toString())
            putString("weight", user?.physicalAttributes?.weight.toString())
            putString("height", user?.physicalAttributes?.height.toString())
            putString("bloodType", user?.physicalAttributes?.bloodType)
            putString("gender", user?.physicalAttributes?.gender)

            // Preferences info
            putString("language", user?.preferences?.language)
            putString("theme", user?.preferences?.theme)

            // Contact info
            putString("phone", user?.contactInfo?.phone)
            putString("street", user?.contactInfo?.address?.street)
            putString("city", user?.contactInfo?.address?.city)
            putString("province", user?.contactInfo?.address?.province)
            putString("country", user?.contactInfo?.address?.country)
            putString("zipCode", user?.contactInfo?.address?.zipCode)

            // Social Media info
            putString("facebook", user?.socialLinks?.facebook)
            putString("twitter", user?.socialLinks?.twitter)

            putString("biography", user?.biography)

            apply()
        }
        Log.d("SessionManager", """
            User session saved: 
            uid=${user?.id},
            userId=${user?.userId},
            email=${user?.email}, 
            name=${user?.name}, 
            age=${user?.physicalAttributes?.age},
            weight=${user?.physicalAttributes?.weight},
            height=${user?.physicalAttributes?.height},
            bloodType=${user?.physicalAttributes?.bloodType},
            gender=${user?.physicalAttributes?.gender},
            language=${user?.preferences?.language},
            theme=${user?.preferences?.theme},
            phone=${user?.contactInfo?.phone}, 
            street=${user?.contactInfo?.address?.street}, 
            city=${user?.contactInfo?.address?.city}, 
            province=${user?.contactInfo?.address?.province}, 
            country=${user?.contactInfo?.address?.country}, 
            zipCode=${user?.contactInfo?.address?.zipCode}
            facebook=${user?.socialLinks?.facebook},
            twitter=${user?.socialLinks?.twitter},
            biography=${user?.biography}
        """.trimIndent())
    }

    fun saveIdSession(context: Context, uid: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_UID, uid)
            apply()
        }
    }

    fun saveUserIdSession(context: Context, userId: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("userId", userId)
            apply()
        }
    }

    fun getUserSession(context: Context): Map<String, String?> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return mapOf(
            KEY_UID to prefs.getString(KEY_UID, null),
            "userId" to prefs.getString("userId", null),
            "email" to prefs.getString("email", null),
            "name" to prefs.getString("name", null),
            "age" to prefs.getString("age", null),
            "weight" to prefs.getString("weight", null),
            "height" to prefs.getString("height", null),
            "bloodType" to prefs.getString("bloodType", null),
            "gender" to prefs.getString("gender", null),
            "language" to prefs.getString("language", null),
            "theme" to prefs.getString("theme", null),
            "phone" to prefs.getString("phone", null),
            "street" to prefs.getString("street", null),
            "city" to prefs.getString("city", null),
            "province" to prefs.getString("province", null),
            "country" to prefs.getString("country", null),
            "zipCode" to prefs.getString("zipCode", null),
            "facebook" to prefs.getString("facebook", null),
            "twitter" to prefs.getString("twitter", null),
            "biography" to prefs.getString("biography", null)
        )
    }

    fun clearUserSession(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        // prefs.edit().clear().apply()
        prefs.edit { clear() }
    }

}