package com.example.core.data.kategori.remote

import retrofit2.Response
import retrofit2.http.GET

interface KategoriService {
    @GET("products/categories")
    suspend fun getAllCategory(): Response<List<String>>
}