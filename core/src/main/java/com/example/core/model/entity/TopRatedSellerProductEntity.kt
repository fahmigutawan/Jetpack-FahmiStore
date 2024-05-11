package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.model.response.kategori.SingleCategoryResponse

@Entity
data class TopRatedSellerProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val price: Long,
    val description: String,
    val category: SingleCategoryResponse,
    val images: List<String>
)
