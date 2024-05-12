package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DistrictEntity(
    @PrimaryKey
    val id: String,
    val regency_id: String,
    val name: String
)
