package com.example.core.data.auth.local

import android.content.SharedPreferences
import javax.inject.Inject

class AuthLocalSource @Inject constructor(
    private val pref: SharedPreferences
) {
    fun getToken() = pref.getString("token", "") ?: ""

    fun saveToken(token: String) = pref.edit().putString("token", token).apply()
}