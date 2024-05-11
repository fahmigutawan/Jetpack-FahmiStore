package com.example.core.model.response.product

data class SingleProductResponse(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)