package com.example.core.data.product.remote

import com.example.core.model.response.product.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getProductsByOffsetAndLimit(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<List<SingleProductResponse>>
}