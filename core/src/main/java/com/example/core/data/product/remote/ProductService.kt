package com.example.core.data.product.remote

import com.example.core.model.response.product.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getProductsByOffsetAndLimit(
        @Query("limit") limit: Int
    ): Response<List<SingleProductResponse>>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): Response<List<SingleProductResponse>>
}