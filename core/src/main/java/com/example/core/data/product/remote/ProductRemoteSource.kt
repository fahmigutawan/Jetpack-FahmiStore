package com.example.core.data.product.remote

import javax.inject.Inject

class ProductRemoteSource @Inject constructor(
    private val service: ProductService
) {
    suspend fun getProductsByOffsetAndLimit(
        limit: Int
    ) = service.getProductsByOffsetAndLimit(limit)
}