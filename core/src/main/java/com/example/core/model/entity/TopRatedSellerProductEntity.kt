package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TopRatedSellerProductEntity(
    @PrimaryKey    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)
