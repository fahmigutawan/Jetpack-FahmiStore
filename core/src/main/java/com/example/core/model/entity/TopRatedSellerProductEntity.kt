package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.model.response.product.ProductRatingResponse

@Entity
data class TopRatedSellerProductEntity(
    @PrimaryKey    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRatingResponse
)
