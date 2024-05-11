package com.example.core.data.product.remote

import javax.inject.Inject

class ProductRemoteSource @Inject constructor(
    private val service: ProductService
) {
    suspend fun getProductsWithLimit(
        limit: Int
    ) = service.getProductsByOffsetAndLimit(limit)

    suspend fun getAllProduct() = service.getAllProduct()

    suspend fun getProductByCategory(category: String) = service.getProductsByCategory(category)
}