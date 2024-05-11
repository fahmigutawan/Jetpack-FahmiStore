package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BannerEntity(
    @PrimaryKey
    val id: String,
    val image: String
)
