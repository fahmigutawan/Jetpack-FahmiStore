package com.example.core.data.kategori.remote

import com.example.core.model.response.kategori.SingleCategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface KategoriService {
    @GET("categories")
    suspend fun getAllCategory(): Response<List<SingleCategoryResponse>>
}