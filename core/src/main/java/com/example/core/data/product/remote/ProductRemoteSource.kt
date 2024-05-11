package com.example.core.data.product.remote

import javax.inject.Inject

class ProductRemoteSource @Inject constructor(
    private val service: ProductService
) {
    suspend fun getProductsByOffsetAndLimit(
        offset: Int,
        limit: Int
    ) = service.getProductsByOffsetAndLimit(offset, limit)
}