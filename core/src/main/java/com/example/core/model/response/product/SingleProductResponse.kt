package com.example.core.model.response.product

import com.example.core.model.response.kategori.SingleCategoryResponse

data class SingleProductResponse(
    val id: String,
    val title: String,
    val price: Long,
    val description: String,
    val category: SingleCategoryResponse,
    val images: List<String>
)