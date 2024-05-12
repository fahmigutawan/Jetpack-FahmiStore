package com.example.core.data.auth

import com.example.core.data.auth.local.AuthLocalSource
import com.example.core.data.auth.remote.AuthRemoteSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remote: AuthRemoteSource,
    private val local: AuthLocalSource
) {
    fun getToken() = local.getToken()

    fun saveToken(token: String) = local.saveToken(token)
}